drop table if exists "public"."hostsystem";
CREATE TABLE "public"."hostsystem" (
"hostname" varchar(255) DEFAULT NULL::character varying,
"ostype" varchar(255) DEFAULT NULL::character varying,
"vendor" varchar(255) DEFAULT NULL::character varying,
"id" int4 DEFAULT NULL NOT NULL,
"resid" int4 DEFAULT NULL,
"dcid" int4 DEFAULT NULL,
CONSTRAINT "hostsystem_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

DROP SEQUENCE  IF EXISTS "public"."host_system_id_seq";
CREATE SEQUENCE "public"."host_system_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
 
drop table if exists "public"."virtualmachine";
CREATE TABLE "public"."virtualmachine" (
"id" int4 DEFAULT NULL NOT NULL,
"name" varchar DEFAULT NULL NOT NULL,
"memorysize" int8 DEFAULT NULL,
"cpucount" int4 DEFAULT NULL,
"ostype" varchar DEFAULT NULL,
"hostid" int4 DEFAULT NULL NOT NULL,
CONSTRAINT "virtualmachine_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

DROP SEQUENCE  IF EXISTS "public"."vm_id_seq";
CREATE SEQUENCE "public"."vm_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
 
 drop table if exists "public"."datastore";
CREATE TABLE "public"."datastore" (
"id" int4 DEFAULT NULL NOT NULL,
"name" varchar DEFAULT NULL NOT NULL,
"url" varchar DEFAULT NULL,
"freespace" int8 DEFAULT NULL,
"maxfilesize" int8 DEFAULT NULL,
"dcid" int4 DEFAULT NULL,
"host" varchar DEFAULT NULL
)
WITH (OIDS=FALSE)
;

DROP SEQUENCE  IF EXISTS "public"."data_store_id_seq";
CREATE SEQUENCE "public"."data_store_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 14
 CACHE 1;
 
  drop table if exists "public"."datacenter";
 CREATE TABLE "public"."datacenter" (
"id" int4 DEFAULT NULL NOT NULL,
"name" varchar DEFAULT NULL NOT NULL
)
WITH (OIDS=FALSE)
;

DROP SEQUENCE  IF EXISTS "public"."dc_id_seq";
CREATE SEQUENCE "public"."dc_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
 
 drop table if exists "public"."folder";
 CREATE TABLE "public"."folder" (
"id" int4 DEFAULT NULL,
"name" varchar DEFAULT NULL,
"dcid" int4 DEFAULT NULL,
"childtype" varchar DEFAULT NULL
)
WITH (OIDS=FALSE)
;

 DROP SEQUENCE  IF EXISTS "public"."folder_id_seq";
 CREATE SEQUENCE "public"."folder_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

 drop table if exists "public"."computeresource";
 CREATE TABLE "public"."computeresource" (
"id" int4 DEFAULT NULL NOT NULL,
"name" varchar DEFAULT NULL NOT NULL,
"type" varchar DEFAULT NULL
)
WITH (OIDS=FALSE)
;

DROP SEQUENCE  IF EXISTS "public"."compute_res_id_seq";
CREATE SEQUENCE "public"."compute_res_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;