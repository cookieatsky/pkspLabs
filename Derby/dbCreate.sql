DROP TABLE Service;

CREATE TABLE Service
(
    id              BIGINT         NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name            VARCHAR(40),
    description     VARCHAR(255),
    duration        INTEGER,
    cost            DECIMAL(8,2),
    category VARCHAR(40)
);

INSERT INTO Service VALUES (DEFAULT,  'Thailand massage', 'thailand massage', 45, 20.50, 'MASSAGE');
INSERT INTO Service VALUES (DEFAULT,  'Manicure', 'Manicure', 60, 10.50, 'MANICURE');

COMMIT;

/* 
Here are our naming conventions 

    Table Names: CamelCase
        Start with cap, because then they map directly to Java class names. 
        We're using these a lot in Java labs, so might as well be consistent with those conventions.
        Singluar.
    Column Names: camelCase
        Start with lowercase letter - again to be consistent with Java classes. 
        Also differentiates between table and column names.
    Table primary key id column: Always called "id"
        e.g. in Item table -
            CREATE TABLE Item (id           BIGINT     ... )
    Master-Detail detail table names: MasterTable_DetailName
    	e.g. BandMember detail table for Item is now Item_BandMember
    Foreign key columns: camelCase of form otherTableId
        e.g. in Inventory table, which has a foreign key column to Item
            itemId     BIGINT       NOT NULL,
    Primary Key Constraint declaration: PK_TableName
        e.g. for Item
            CONSTRAINT    PK_Item  PRIMARY KEY(id)
    Foreign Key Constraint declaration: FK_ThisTableAbbreviated_OtherTable
        e.g. for Inventory foreign key to Item
            CONSTRAINT      FK_IV_to_Item  FOREIGN KEY(itemId) REFERENCES Item(id)
    Other constraints: As needed.
        e.g. Unique constraint on in Inventory on ItemId and location
               CONSTRAINT      UNQ_itemId_location     UNIQUE(itemId, location),
*/
