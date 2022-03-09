
-- psql -U postgres -d ecole -f D:\ITU\S3\PROJETS5S3\Ecole\7Mars\script_ecole.sql
CREATE SEQUENCE adminseq;
CREATE SEQUENCE classement;
CREATE SEQUENCE "drop";
CREATE SEQUENCE eco;
CREATE SEQUENCE etu;
CREATE SEQUENCE mat;
CREATE SEQUENCE niv;
CREATE SEQUENCE noteseq;
CREATE SEQUENCE notev;
CREATE SEQUENCE profseq;
CREATE SEQUENCE prom;
CREATE SEQUENCE secre;


CREATE TABLE "admin" (
    id integer NOT NULL,
    mdp varchar(50),
    identifiant varchar(50),
    CONSTRAINT pk_tbl_0 PRIMARY KEY (id)
);


CREATE TABLE niveau (
    id integer NOT NULL,
    nom varchar(50),
    ecolage decimal,
    CONSTRAINT pk_niveau PRIMARY KEY (id)
);


CREATE TABLE promotion (
    id integer NOT NULL,
    nom varchar(50),
    statut integer,
    actuel integer,
    CONSTRAINT pk_promotion PRIMARY KEY (id)
);

CREATE TABLE secretaire (
    id integer NOT NULL,
    nom varchar(150),
    mdp varchar(50),
    identifiant varchar(50),
    CONSTRAINT pk_secretaire PRIMARY KEY (id)
);

CREATE TABLE etudiant (
    id integer NOT NULL,
    nom varchar(50),
    prenom varchar(250),
    idpromotion integer references promotion(id),
    idniveau integer references niveau(id),
    datenaissance date,
    etu varchar(50),
    sexe varchar(1),
    adresse varchar(50),
    CONSTRAINT pk_tbl PRIMARY KEY (id),
    CONSTRAINT unq_etudiant1_idpromotion UNIQUE (id,idpromotion),
    CONSTRAINT unq_etudiant1_idniveau UNIQUE (id,idniveau)
);



CREATE TABLE prof (
    id integer NOT NULL,
    nom varchar(250),
    mdp varchar(50),
    identifiant varchar(50),
    CONSTRAINT pk_prof PRIMARY KEY (id)
);


CREATE TABLE matiere (
    id integer NOT NULL,
    nom varchar(50),
    coeff integer,
    idniveau integer references niveau(id),
    idprof integer references prof(id),
    nbexamen integer,
    CONSTRAINT pk_matiere PRIMARY KEY (id),
    CONSTRAINT unq_matiere_idniveau UNIQUE (id,idniveau),
    CONSTRAINT unq_matiere_idprof UNIQUE (id,idprof)
);

CREATE TABLE note (
    id integer NOT NULL,
    idetudiant integer references etudiant(id),
    idmatiere integer references matiere(id),
    note decimal(600),
    "date" date DEFAULT CURRENT_DATE,
    CONSTRAINT pk_note PRIMARY KEY (id),
    CONSTRAINT unq_note_idmatiere UNIQUE (id,idmatiere),
    CONSTRAINT unq_note_idetudiant UNIQUE (id,idetudiant)
);

CREATE TABLE notevalide (
    id integer NOT NULL,
    idnote integer REFERENCES note(id),
    CONSTRAINT pk_notevalide PRIMARY KEY (id),
    CONSTRAINT unq_notevalide_idnote UNIQUE (idnote)
);



CREATE TABLE classementnote (
    id integer NOT NULL,
    nom varchar(100),
    prenom varchar(250),
    idetu integer references etudiant(id),
    etu varchar(50),
    moyenne decimal,
    idpromotion integer references promotion,
    idniveau integer references niveau(id),
    merite varchar(5),
    CONSTRAINT pk_classementnote PRIMARY KEY (id),
    CONSTRAINT unq_classementnote_idetu UNIQUE (idetu),
    CONSTRAINT unq_classementnote_idniveau UNIQUE (id,idniveau),
    CONSTRAINT unq_classementnote_idpromotion UNIQUE (id,idpromotion)
);

CREATE TABLE ecolageetudiant (
    id integer NOT NULL,
    idetudiant integer references etudiant(id),
    montant decimal,
    mois integer,
    annee integer,
    "date" date DEFAULT CURRENT_DATE,
    idniveau integer references niveau(id),
    CONSTRAINT pk_ecolageetudiant PRIMARY KEY (id),
    CONSTRAINT unq_ecolageetudiant_idniveau UNIQUE (id,idniveau),
    CONSTRAINT unq_ecolageetudiant_idetudiant UNIQUE (id,idetudiant)
);

CREATE TABLE etudiantcorbeille (
    id integer NOT NULL,
    idetudiant integer references etudiant(id),
    CONSTRAINT pk_etudiantcorbeille PRIMARY KEY (id),
    CONSTRAINT unq_etudiantcorbeille_idetudiant UNIQUE (idetudiant)
);


CREATE OR REPLACE VIEW detailetudiant AS
SELECT e.id idetudiant,e.nom,
    e.prenom,
    e.etu,
    e.datenaissance,
    e.sexe,
    e.adresse,
    p.id idpromotion,
    p.nom as promotion,
    p.statut prom_statut,
    n.id idniveau,
    n.nom as niveau
FROM etudiant e
    join promotion p on p.id = e.idpromotion
    join niveau n on n.id = e.idniveau
where e.id not in (SELECT idetudiant from etudiantcorbeille);


CREATE OR REPLACE VIEW SommeEcolageEtudiant as
SELECT e.id idetudiant,
    e.nom || ' ' || e.prenom nomEtudiant,
    e.sexe,
    e.datenaissance,
    e.adresse,
    e.etu,
    sum(ec.montant) montant,
    ec.mois,
    ec.annee,
    n.id idniveau,
    n.nom niv,
    n.ecolage,
    p.id idpromotion,
    p.nom prom
FROM etudiant e
    left join ecolageetudiant ec on e.id = ec.idetudiant
    right join niveau n on ec.idniveau = n.id
    join promotion p on e.idpromotion = p.id
group by e.id,
    e.nom,
    e.prenom,
    e.sexe,
    e.datenaissance,
    e.adresse,
    e.etu,
    ec.mois,
    ec.annee,
    n.id,
    n.ecolage,
    p.id,
    p.nom;


CREATE OR REPLACE VIEW ecolageNonPayeEtudiant as
SELECT 
idetudiant,
etu,
nomEtudiant,
ecolage-montant reste,
mois,
annee,
idpromotion,
prom,
idniveau,
niv
FROM SommeEcolageEtudiant where (ecolage-montant)!=0;




CREATE OR REPLACE VIEW ecolageetudiantSemestre as
SELECT e.id idetudiant,
    e.nom,
    e.prenom,
    e.sexe,
    e.adresse,
    e.etu,
    sum(ec.montant) totalEcolage,
    n.id idniveau,
    n.nom niv,
    n.ecolage * 6 ecolageSemestre,
    p.id idpromotion,
    p.nom prom
FROM etudiant e
    left join ecolageetudiant ec on e.id = ec.idetudiant
    right join niveau n on n.id = ec.idniveau join promotion p on p.id=e.idpromotion
    group by e.id,e.nom,e.prenom,e.sexe,e.adresse,e.etu,n.id,n.nom,n.ecolage,p.id,p.nom;


CREATE OR REPLACE VIEW NoteNonValide as
SELECT 
m.id idmatiere,m.nom matiere,m.coeff,
n.id idnote,n.note,n."date",de.*
FROM matiere m join note n on m.id=n.idmatiere join detailetudiant de on n.idetudiant=de.idetudiant
where n.id not in (SELECT idnote from notevalide);

CREATE OR REPLACE VIEW NoteValideMatiere AS
SELECT 
m.id idmatiere,m.nom matiere,m.coeff,sum(n.note)/m.nbexamen totalNote,n.idetudiant
FROM matiere m join note n on m.id=n.idmatiere join notevalide nv on nv.idnote=n.id
group by m.id,m.nom,m.coeff,m.nbexamen,n.idetudiant;







