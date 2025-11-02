-- Esquema de base de datos para SQLite
CREATE TABLE IF NOT EXISTS habitantes (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nombre TEXT NOT NULL,
  apellido TEXT NOT NULL,
  edad INTEGER,
  direccion TEXT,
  telefono TEXT,
  fecha_registro TEXT
);

CREATE TABLE IF NOT EXISTS expedientes (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  habitante_id INTEGER NOT NULL,
  tipo_expediente TEXT NOT NULL,
  descripcion TEXT,
  fecha_creacion TEXT,
  estado TEXT,
  FOREIGN KEY(habitante_id) REFERENCES habitantes(id)
);

CREATE TABLE IF NOT EXISTS cooperaciones (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  habitante_id INTEGER NOT NULL,
  tipo_cooperacion TEXT NOT NULL,
  descripcion TEXT,
  fecha_cooperacion TEXT,
  puntos INTEGER,
  FOREIGN KEY(habitante_id) REFERENCES habitantes(id)
);

CREATE TABLE IF NOT EXISTS faenas (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  habitante_id INTEGER NOT NULL,
  tipo_faena TEXT NOT NULL,
  descripcion TEXT,
  fecha_participacion TEXT,
  horas_trabajadas INTEGER,
  FOREIGN KEY(habitante_id) REFERENCES habitantes(id)
);

CREATE TABLE IF NOT EXISTS sesiones (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  usuario TEXT NOT NULL,
  token TEXT NOT NULL,
  inicio TEXT,
  ultimo_acceso TEXT,
  activa INTEGER NOT NULL
);












