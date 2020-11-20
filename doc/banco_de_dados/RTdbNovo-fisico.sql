/* rtracker: */

CREATE TABLE filiais (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50),
    cnpj VARCHAR(18),
    cidade VARCHAR(30),
    estado VARCHAR(2),
    rntrc VARCHAR(8)
);

CREATE TABLE funcionarios_filiais (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(50),
    cargo VARCHAR(13),
    email VARCHAR(50),
    senha VARCHAR(32),
    fk_filiais_id SERIAL
);

CREATE TABLE motoristas (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(50),
    turno VARCHAR(11),
    dom BOOLEAN,
    ter BOOLEAN,
    qua BOOLEAN,
    qui BOOLEAN,
    sab BOOLEAN,
    cargahoraria VARCHAR(4),
    salario VARCHAR(10),
    sex BOOLEAN,
    email VARCHAR(50),
    dia_atual VARCHAR(2),
    trabalhado_hoje VARCHAR(5),
    descansado_hoje VARCHAR(5),
    alimentacao_hoje VARCHAR(5),
    senha VARCHAR(32),
    cargo VARCHAR(13),
    seg BOOLEAN,
    fk_filiais_id SERIAL
);

CREATE TABLE avisos (
    id SERIAL PRIMARY KEY,
    mensagem TEXT,
    data VARCHAR(10),
    resolvido BOOLEAN,
    nomenclatura VARCHAR(15),
    emissor VARCHAR(14),
    fk_motoristas_cpf VARCHAR(14)
);

CREATE TABLE veiculos (
    placa VARCHAR(8) PRIMARY KEY,
    id_rastreador VARCHAR(20),
    marca_rastreador VARCHAR(20),
    modelo_rastreador VARCHAR(20),
    modelo_veiculo VARCHAR(20),
    fk_filiais_id SERIAL
);

CREATE TABLE viagens (
    id SERIAL PRIMARY KEY,
    carga VARCHAR(20),
    inicio VARCHAR(10),
    fim VARCHAR(10),
    total VARCHAR(08),
    situacao VARCHAR(12),
    destino VARCHAR(30),
    fk_veiculos_placa VARCHAR(8),
    fk_motoristas_cpf VARCHAR(14)
);

CREATE TABLE status (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20),
    inicio VARCHAR(10),
    fim VARCHAR(10),
    total VARCHAR(8),
    fk_motoristas_cpf VARCHAR(14)
);

CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    registro TEXT,
    horario VARCHAR(8),
    data VARCHAR(10),
    fk_funcionarios_filiais_cpf VARCHAR(14)
);
 
ALTER TABLE funcionarios_filiais ADD CONSTRAINT FK_funcionarios_filiais_2
    FOREIGN KEY (fk_filiais_id)
    REFERENCES filiais (id)
    ON DELETE RESTRICT;
 
ALTER TABLE motoristas ADD CONSTRAINT FK_motoristas_2
    FOREIGN KEY (fk_filiais_id)
    REFERENCES filiais (id)
    ON DELETE RESTRICT;
 
ALTER TABLE avisos ADD CONSTRAINT FK_avisos_2
    FOREIGN KEY (fk_motoristas_cpf)
    REFERENCES motoristas (cpf)
    ON DELETE RESTRICT;
 
ALTER TABLE veiculos ADD CONSTRAINT FK_veiculos_2
    FOREIGN KEY (fk_filiais_id)
    REFERENCES filiais (id)
    ON DELETE RESTRICT;
 
ALTER TABLE viagens ADD CONSTRAINT FK_viagens_2
    FOREIGN KEY (fk_veiculos_placa)
    REFERENCES veiculos (placa)
    ON DELETE RESTRICT;
 
ALTER TABLE viagens ADD CONSTRAINT FK_viagens_3
    FOREIGN KEY (fk_motoristas_cpf)
    REFERENCES motoristas (cpf)
    ON DELETE RESTRICT;
 
ALTER TABLE status ADD CONSTRAINT FK_status_2
    FOREIGN KEY (fk_motoristas_cpf)
    REFERENCES motoristas (cpf)
    ON DELETE RESTRICT;
 
ALTER TABLE logs ADD CONSTRAINT FK_logs_2
    FOREIGN KEY (fk_funcionarios_filiais_cpf)
    REFERENCES funcionarios_filiais (cpf)
    ON DELETE RESTRICT;