CREATE TABLE Craft (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE Grade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE WORKER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    grade_id BIGINT,
    craft_id BIGINT,
    FOREIGN KEY (grade_id) REFERENCES Grade(id),
    FOREIGN KEY (craft_id) REFERENCES Craft(id)
);

CREATE TABLE Qualification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    daysUntilExpiration BIGINT NULL, -- If NULL, qualification never expires
    craft_id BIGINT NULL, -- If NULL, qualification is applicable to all crafts
    FOREIGN KEY (craft_id) REFERENCES Craft(id)
);

CREATE TABLE WorkerQualificationStatus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    qualificationId BIGINT,
    workerId BIGINT,
    assignedOn DATE,
    completedOn DATE NULL,
    expiration DATE NULL,
    FOREIGN KEY (qualificationId) REFERENCES Qualification(id),
    FOREIGN KEY (workerId) REFERENCES WORKER(id)
);

INSERT INTO Craft (name) VALUES
('Electrician'),
('Pipefitter'),
('Welder'),
('Plumber'),
('Carpenter');


INSERT INTO Grade (name) VALUES
('Journeyman'),
('Master'),
('Apprentice'),
('Foreman');

-- Inserting Qualifications
INSERT INTO Qualification (name, daysUntilExpiration, craft_id) VALUES
-- Should be required by all
('Rad Worker', 730, NULL),
('Plant Access', 730, NULL),
('Fitness for Duty', 365, NULL),
-- Assuming the IDs for crafts are as follows: Electrician=1, Pipefitter=2, Welder=3, Plumber=4, Carpenter=5
('Circuit Wizard', 30, (SELECT id FROM Craft WHERE name = 'Electrician')),
('Electrical Safety', NULL, (SELECT id FROM Craft WHERE name = 'Electrician')),
('Melting Metals', 500, (SELECT id FROM Craft WHERE name = 'Welder')),
('Draino Qualified', NULL, (SELECT id FROM Craft WHERE name = 'Plumber')),
('Hammering', 250, (SELECT id FROM Craft WHERE name = 'Carpenter')),
('Drilling', 365, (SELECT id FROM Craft WHERE name = 'Carpenter')),
('Safety Training', 365, NULL),
('Advanced Welding', 730, (SELECT id FROM Craft WHERE name = 'Welder')),
('Piping Systems', 365, (SELECT id FROM Craft WHERE name = 'Pipefitter')),
('Electrical Code Compliance', 730, (SELECT id FROM Craft WHERE name = 'Electrician')),
('Soldering Basics', 180, (SELECT id FROM Craft WHERE name = 'Plumber')),
('Carpentry 101', 365, (SELECT id FROM Craft WHERE name = 'Carpenter')),
('Project Management', 0, NULL),
('HVAC Fundamentals', 730, (SELECT id FROM Craft WHERE name = 'Pipefitter')),
('Concrete Technology', 365, (SELECT id FROM Craft WHERE name = 'Carpenter')),
('First Aid Certification', 365, NULL),
('High Voltage Systems', 365, (SELECT id FROM Craft WHERE name = 'Electrician')),
('Water Systems', 365, (SELECT id FROM Craft WHERE name = 'Plumber')),
('Gas Fitting', 730, (SELECT id FROM Craft WHERE name = 'Pipefitter')),
('Metal Fabrication', 365, (SELECT id FROM Craft WHERE name = 'Welder')),
('Roofing Techniques', 730, (SELECT id FROM Craft WHERE name = 'Carpenter')),
('Risk Assessment', 365, NULL);