USE vcv;

# ==============================================================
# = Dummy Data
# ==============================================================
INSERT INTO `User` VALUES
    ('RSJMorris@gmail.com',      'abcxyz123',     0, 3, 1),
    ('JaneDoe@trident.com',      'tridentRUs',    0, 1, 2),
    ('GeorgeOrwell@trident.com', 'p@ssw0rd65',    0, 2, 2),
    ('Jeffery@courtesy.com',     'typica757l',    0, 1, 3),
    ('Jen@courtesy.com',         '5brgeg22bv',    0, 2, 3),
    ('T_man@hotmail.com',        'pafw35rg23',    0, 2, 4),
    ('PCadwell@mqi.bb.com',      '5yh35trbefgd4', 0, 1, 5),
    ('YKlint@mqi.bb.com',        '45htrgf575',    0, 2, 5),
    ('Jane.Peralta@sagicor.com', 'e4rwg325qwsd',  0, 2, 6);

INSERT INTO `Company` VALUES
    (2, 'Trident Insurance',     'Insurance',  2019-04-01 00:00:00, 2020-04-01 00:00:00, 0, 'https://tridentins.com', 'GeorgeOrwell@trident.com', 0, 1),
    (3, 'Courtesy Garage',       'Garage',     2019-04-01 00:00:00, 2020-04-01 00:00:00, 0, 'https://courtesy.com',   'Jen@courtesy.com',         0, 1),
    (4, 'Tramaine Shop',         'Mechanic',   2019-04-01 00:00:00, 2019-04-01 00:00:00, 0, '',                       'T_man@hotmail.com',        0, 1),
    (5, 'MQI',                   'Dealership', 2019-04-01 00:00:00, 2020-04-01 00:00:00, 0, 'https://mqi.com',        'YKlint@mqi.bb.com',        0, 1),
    (6, 'Sagicor Car Insurance', 'Mechanic',   2019-04-01 00:00:00, 2019-04-01 00:00:00, 0, 'https://sagicor.com',    'T_man@hotmail.com',        0, 1);

INSERT INTO `Policy` VALUES
    (2, 'ABC123JKL789', 'Frank Johnson',   'Third Party',   2015-06-25 00:00:00, 'Royal Bank of Canada',               1, 'MPATFS85JDT000196'),
    (2, 'JUFOP48985K4', 'Sina Dawson',     'Third Party',   2004-12-12 00:00:00, 'Capita Finance',                     1, 'JHMKB2650AC200233'),
    (2, '4JMIYLE423SD', 'Gina Gibbins',    'Third Party',   1992-05-23 00:00:00, 'Capita Finance',                     0, 'MMBJRKB40CD043653'),
    (2, 'FDGNFRG345FG', 'Gina Gibbins',    'Comprehensive', 2016-09-03 00:00:00, '',                                   0, 'MMBJRKB40CD043653'),
    (6, 'FGHFGH653RVC', 'Gina Gibbins',    'Third Party',   2019-01-13 00:00:00, '',                                   1, 'MMBJRKB40CD043653'),
    (2, 'DTGNKIGHG23S', 'James von Hurst', 'Comprehensive', 2014-03-07 00:00:00, 'Global Finance',                     1, 'MPATFS85JJT001446'),
    (2, 'EDTHRT234BDF', 'James von Hurst', 'Third Party',   2002-01-19 00:00:00, 'First Caribbean International Bank', 1, 'KLATF69YEWB248992');

INSERT INTO `Claim` VALUES
    (2, 'SDFG1634DSER5TE', 'Total Loss',      2014-04-23 00:00:00, 'Flipped into a ravine',                                                          65000, '4JMIYLE423SD', 'MMBJRKB40CD043653', 1),
    (2, 'G534JNUK65MTTE4', 'Liability',       2012-02-04 00:00:00, 'Rear-ended another vehicle, BSDFG@ASDB43WDF@3',                                  700,   'ABC123JKL789', 'MPATFS85JDT000196', 2),
    (2, 'FSGH7867T23RGBD', 'Personal Injury', 2002-08-11 00:00:00, 'Accident related from 17/03/2002; Claim # FDHGR765FVASDAF',                      1200,  'JUFOP48985K4', 'MPATFS85JDT000196', 0),
    (2, 'FDHGR765FVASDAF', 'Accident',        2009-09-21 00:00:00, 'T-Boned at intersection by vehicle KLAITF45SJD123KA. Contacted Insurance @ CGI', 0,     'JUFOP48985K4', 'JHMKB2650AC200233', 0);

INSERT INTO `Job` VALUES
    (1, 'Accident', 2009-10-07 00:00:00, 1200, 'Left-back door was completely folded in and needed to be replaced. Side skirts were adjusted. Wheel realignment done', 3, 2, 'FDHGR765FVASDAF', 'JHMKB2650AC200233'),
    (2, 'Service',  2018-11-29 00:00:00, 80,   '',                                                                                                                     3, 0, '',                'MMBJRKB40CD043653'),
    (3, 'Upgrade',  1998-07-12 00:00:00, 1700, 'Gearbox change from AT to MT',                                                                                         4, 0, '',                'KLATF69YEWB248992'),
    (4, 'Repair',   2006-02-30 00:00:00, 150,  'Replace left steering-rawding',                                                                                        4, 0, '',                'MPATFS85JJT001446');

INSERT INTO `Vehicle` VALUES
    ('MPATFS85JDT000196', 2011, 'Isuzu',      'Bobbette', 'blue',   70000, 135000, 'MQI', 'Isuzu, America West', 'Manual',         'Diesel',   'Right Hand Drive', '2000 CC', 'All-Wheel Drive',   'Truck', 3, 2, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 1, 0, 3, 0, 1, 2, 'ABC123JKL789'),
    ('MPATFS85JDT000196', 2011, 'Isuzu',      'Bobbette', 'blue',   70000, 135000, 'MQI', 'Isuzu, America West', 'Manual',         'Diesel',   'Right Hand Drive', '2000 CC', 'All-Wheel Drive',   'Truck', 3, 2, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 1, 0, 3, 0, 1, 2, 'ABC123JKL789'),
    ('JHMKB2650AC200233', 2009, 'Honda',      'Sunpwer',  'silver', 45500, 25000,  'MQI', 'Honda, Japan',        'Automatic',      'Gasoline', 'Right Hand Drive', '1600 CC', 'Front-Wheel Drive', 'Sedan', 5, 5, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 2, 0, 3, 0, 4, 2, 'JUFOP48985K4'),
    ('MMBJRKB40CD043653', 2010, 'Mitsubishi', 'Evo',      'black',  85000, 123550, 'MQI', 'Mitsubishi, Japan',   'Semi-Automatic', 'Gasoline', 'Right Hand Drive', '2000 CC', 'Rear-Wheel Drive',  'Sedan', 5, 5, 0, 1, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 0, 0, 6, 1, 1, 6, '4JMIYLE423SD'),
    ('MPATFS85JJT001446', 2004, 'Isuzu',      'Boop',     'red',    12000, 234550, 'MQI', 'Isuzu, Japan',        'Automatic',      'Gasoline', 'Left Hand Drive',  '1300 CC', 'Front-Wheel Drive', 'Coup',  5, 3, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 0, 1, 2, 0, 2, 2, 'DTGNKIGHG23S'),
    ('KLATF69YEWB248992', 1992, 'Daewoo',     'Eww',      'yellow', 7000,  155625, 'MQI', 'Daewoo, Japan',       'Automatic',      'Gasoline', 'Right Hand Drive', '1300 CC', 'Front-Wheel Drive', 'Sedan', 5, 5, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 1, 0, 0, 2, 1, 2, 'EDTHRT234BDF');