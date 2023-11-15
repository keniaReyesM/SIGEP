USE sigep;

DROP TABLE IF EXISTS permiso_proyecto_permiso_rol;
DROP TABLE IF EXISTS permiso_rol;
DROP TABLE IF EXISTS permiso;

ALTER TABLE cliente DROP COLUMN servidor_alojamiento;
ALTER TABLE cliente DROP COLUMN nombre_pagina;
ALTER TABLE asignacion_proyecto DROP COLUMN permiso_proyecto_id;
ALTER TABLE cliente ADD COLUMN estado_id INT NOT NULL;
