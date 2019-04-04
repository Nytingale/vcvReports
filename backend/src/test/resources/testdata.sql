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
    (2, 'TridentInsurance',    'Insurance',  2019-04-01 00:00:00, 2020-04-01 00:00:00, 0, 'https://tridentins.com', 'GeorgeOrwell@trident.com', 0, 1),
    (3, 'CourtesyGarage',      'Garage',     2019-04-01 00:00:00, 2020-04-01 00:00:00, 0, 'https://courtesy.com',   'Jen@courtesy.com',         0, 1),
    (4, 'TramaineShop',        'Mechanic',   2019-04-01 00:00:00, 2019-04-01 00:00:00, 0, '',                       'T_man@hotmail.com',        0, 1),
    (5, 'MQI',                 'Dealership', 2019-04-01 00:00:00, 2020-04-01 00:00:00, 0, 'https://mqi.com',        'YKlint@mqi.bb.com',        0, 1),
    (6, 'SagicorCarInsurance', 'Insurance',  2019-04-01 00:00:00, 2019-04-01 00:00:00, 0, 'https://sagicor.com',    'T_man@hotmail.com',        0, 1);

INSERT INTO `Policy` VALUES
    (2, 'ABC123JKL789', 'Frank Johnson',   'Third Party',   2015-06-25 00:00:00, 'Royal Bank of Canada',               1, 'MPATFS85HCT102885'),
    (2, 'JUFOP48985K4', 'Sina Dawson',     'Third Party',   2004-12-12 00:00:00, 'Capita Finance',                     1, 'JSZYA215195102215'),
    (2, '4JMIYLE423SD', 'Gina Gibbins',    'Third Party',   1992-05-23 00:00:00, 'Capita Finance',                     0, 'JN1CNUD22Z0003314'),
    (2, 'FDGNFRG345FG', 'Gina Gibbins',    'Comprehensive', 2016-09-03 00:00:00, '',                                   0, 'JN1CNUD22Z0003314'),
    (6, 'FGHFGH653RVC', 'Gina Gibbins',    'Third Party',   2019-01-13 00:00:00, '',                                   1, 'JN1CNUD22Z0003314'),
    (2, 'DTGNKIGHG23S', 'James von Hurst', 'Comprehensive', 2014-03-07 00:00:00, 'Global Finance',                     1, 'JSAFTD02V00200457'),
    (2, 'EDTHRT234BDF', 'James von Hurst', 'Third Party',   2002-01-19 00:00:00, 'First Caribbean International Bank', 1, 'JM0UFY0W500132109');

INSERT INTO `Claim` VALUES
    (2, 'SDFG1634DSER5TE', 'Total Loss',      2014-04-23 00:00:00, 'Flipped into a ravine',                                                          65000, '4JMIYLE423SD', 'JN1CNUD22Z0003314', 1),
    (2, 'G534JNUK65MTTE4', 'Liability',       2012-02-04 00:00:00, 'Rear-ended another vehicle, BSDFG@ASDB43WDF@3',                                  700,   'ABC123JKL789', 'MPATFS85HCT102885', 2),
    (2, 'FSGH7867T23RGBD', 'Personal Injury', 2002-08-11 00:00:00, 'Accident related from 17/03/2002; Claim # FDHGR765FVASDAF',                      1200,  'JUFOP48985K4', 'MPATFS85HCT102885', 0),
    (2, 'FDHGR765FVASDAF', 'Accident',        2009-09-21 00:00:00, 'T-Boned at intersection by vehicle KLAITF45SJD123KA. Contacted Insurance @ CGI', 0,     'JUFOP48985K4', 'JSZYA215195102215', 0);

INSERT INTO `Job` VALUES
    (1, 'Accident', 2009-10-07 00:00:00, 1200, 'Left-back door was completely folded in and needed to be replaced. Side skirts were adjusted. Wheel realignment done', 3, 2, 'FDHGR765FVASDAF', 'JSZYA215195102215'),
    (2, 'Service',  2018-11-29 00:00:00, 80,   '',                                                                                                                     3, 0, '',                'JN1CNUD22Z0003314'),
    (3, 'Upgrade',  1998-07-12 00:00:00, 1700, 'Gearbox change from AT to MT',                                                                                         4, 0, '',                'JM0UFY0W500132109'),
    (4, 'Repair',   2006-02-30 00:00:00, 150,  'Replace left steering-rawding',                                                                                        4, 0, '',                'JSAFTD02V00200457');

INSERT INTO `Vehicle` VALUES
    ('MPATFS85HCT102885', 2012, 'Isuzu',  'D-Max',        , 70000, 135000, 'MQI', , , , , , , , 3, 2, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 1, 0, 3, 0, 1, 2, 'ABC123JKL789'),
    ('JSZYA215195102215', 2009, 'Suzuki', 'SX4',          , 70000, 135000, 'MQI', , , , , , , , 3, 2, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 1, 0, 3, 0, 1, 2, 'ABC123JKL789'),
    ('JN1CNUD22Z0003314', 2004, 'Nissan', 'Frontier',     , 45500, 25000,  'MQI', , , , , , , , 5, 5, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 2, 0, 3, 0, 4, 2, 'JUFOP48985K4'),
    ('JF1SH9KT5AG032134', 2009, 'Subaru', 'Forrester',    , 85000, 123550, 'MQI', , , , , , , , 5, 5, 0, 1, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 0, 0, 6, 1, 1, 6, '4JMIYLE423SD'),
    ('JSAFTD02V00200457', 2004, 'Suzuki', 'Grand Vitara', , 12000, 234550, 'MQI', , , , , , , , 5, 3, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 0, 1, 2, 0, 2, 2, 'DTGNKIGHG23S'),
    ('JM0UFY0W500132109', 1998, 'Mazda',  'B2500',        , 7000,  155625, 'MQI', , , , , , , , 5, 5, 0, 0, 2018-11-16 00:00:00, 2015-06-05 00:00:00, 1, 0, 0, 2, 1, 2, 'EDTHRT234BDF');