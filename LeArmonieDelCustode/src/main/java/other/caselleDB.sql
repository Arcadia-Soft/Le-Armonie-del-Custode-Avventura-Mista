-- Creazione Casella
CREATE TABLE IF NOT EXISTS Casella (
    id int,
    nome varchar(30),
    descrizioneIniziale text,
    descrizioneAggiuntiva text,
    descrizioneAggiornata text,
    enterable boolean DEFAULT 1,
    PRIMARY KEY (id)
);

-- Creazione tabella di collegamento tra Casella e Casella
CREATE TABLE IF NOT EXISTS ConnessioniCaselle (
    casella_id1 INT,
    casella_id2 INT,
    direzione ENUM('n', 's', 'e', 'o'),
    FOREIGN KEY (casella_id1) REFERENCES Casella(id),
    FOREIGN KEY (casella_id2) REFERENCES Casella(id),
    PRIMARY KEY (casella_id1, casella_id2)
);

-- Creazione Dialoghi
CREATE TABLE IF NOT EXISTS Dialoghi (
    id int,
    priorita boolean DEFAULT false,
    dialogoIniziale text,
    rispostaSeCorretta text,
    rispostaSeErrata text,
    rispostaCorretta text,
    FOREIGN KEY (id) REFERENCES Casella(id),
    PRIMARY KEY (id, priorita)

);

-- Creazione Oggetto
CREATE TABLE IF NOT EXISTS Oggetto (
    id INT PRIMARY KEY,
    nome varchar(30),
    descrizione text,
    quantita int,
    pickable boolean DEFAULT false,
    visible boolean DEFAULT true
);

-- Creazione tabella di collegamento tra Casella e Oggetto
CREATE TABLE IF NOT EXISTS Casella_Oggetto (
    casella_id INT,
    oggetto_id INT,
    quantita INT,
    PRIMARY KEY (casella_id, oggetto_id),
    FOREIGN KEY (casella_id) REFERENCES Casella(id),
    FOREIGN KEY (oggetto_id) REFERENCES Oggetto(id)
);

-- Updated Alias table
CREATE TABLE IF NOT EXISTS Alias (
    id INT,
    alias varchar(30),
    FOREIGN KEY (id) REFERENCES Oggetto(id),
    PRIMARY KEY (id, alias)
);


-- // Ricorda che i campi Nord, sud, est, ovest, indicano le caselle adiacenti nella corrispondente direzione
--         // i numeri negativi indicano che non c'è una casella in quella direzione, e serve per indicare una descrizione di default 
--         // nello specifico:
--         // -9 descrizione di default per il mondo esterno
--         // -1 descrizione di default per il regno di Arcadia
--         // -2 descrizione di default per la foresta
--         // -3 descrizione di default per la grotta
--         // -4 descrizione di default per il cimitero
--         // -5 descrizione di default per il tempio

-- Inserimento Caselle
INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata)
VALUES
(901, 'Casa di Luca', 'Ti trovi nella splendida Valle di Arcadia, una regione incantata circondata da maestose montagne e ricca di paesaggi mozzafiato. La valle è attraversata da fitti boschi di alberi secolari e campi verdissimi. Accanto a te le tue due più care pecore, ti accorgi che forse è il momento di andare al regno da liutaio per sistemare il tuo liuto, prendi tre monete per sicurezza. \n TUTORIAL \n Muoviti tramite i comandi Nord (N), Est (E), Ovest (O), Sud (S), ed arrivare al regno di Arcadia, guarda attentamente ciò che ti circonda, può sempre essere utile!', 'Hai lasciato qui le due pecore prima di andare, sei tornato qui ma non c''è nulla da fare!', ''),
(902, 'Percorso di pietre', 'Stai camminando su un sentiero di pietra che si snoda attraverso una folta foresta. Senti qualcuno parlare davanti a te, nella lontananza senti lo sciabordio del fiume Therm. I tronchi degli alberi sono dei pilastri che sostengono un soffitto di rami e foglie, mentre la luce del sole si insinua timida tra loro, disegnando ombre danzanti ai tuoi piedi. Ci sono tre rami per terra che formano una strana figura.', 'Sei di nuovo sulla tranquilla stradina di casa. Ci sono ancora quei tre rami per terra che formano una strana figura.', ''),
(903, 'Mendicante', 'Incontri un mendicante proprio a pochi passi dal regno, è seduto su una pietra appena incrociate gli occhi sembra voglia parlarti (Per rispondere scrivi "Parla", e dopo, chiederà in input la risposta)', 'Il mendicante è ancora qui, su una pietra, lo senti parlare "Di nuovo tu? Hai qualche moneta?"', ''),
(904, 'Angolo-Ovest Entrata-Sud', 'Questa vallata è famosa per l''eco che rimbalza tra le colline, trasportando canzoni e melodie dai villaggi vicini. Si vede il Castello verso NORD-EST.', 'Sei nella valle degli Echi, a NORD-EST si trova il Castello, mentre a SUD-EST c''è casa tua, ricordi?', ''),
(905, 'Sud Entrata-Sud', 'In lontananza, verso Nord, vedi l''entrata del Regno', 'Vedi lontano, verso SUD, il mendicante è ancora d''avanti a casa, mentre a NORD il Castello ti osserva in tutta la sua maestosità.', ''),
(906, 'Angolo Entrata-Sud', 'Attraversata da un fiume serpeggiante che riflette il Castello, visibile a NORD-OVEST, questa valle è vitale per il regno, fornendo acqua e pesce.', 'Ci si può sedere sulla riva, ammirando come l''acqua scorra e rinnovi la vita a ogni passaggio.', ''),
(907, 'Ovest Entrata-Sud', 'Spesso avvolta da una morbida nebbia mattutina, questa valle si illumina con i primi raggi del sole che sfiorano il Castello sovrastante.', 'Ogni tanto, nella nebbia, verso NORD-EST, si scorgono i contorni del Castello, come un miraggio fatato', ''),
(908, 'Entrata Sud', 'Ti avvicini alle massicce porte del castello, una guardia in armatura d''argento ti osserva con occhi penetranti, poi, con una voce che rimbomba sotto il casco. Potresti provare a parlarci.', 'La guardia è ancora lì, ti guarda un po'' stizzita.', 'Sei tornato davanti a quelle enormi porte, la guardia rimane lì a guardarti.'),
(909, 'Est Entrata-Sud', 'Sei vicino al Regno, potresti andare in tante direzioni, chissà cosa ti aspetterà!', 'Sei vicino all''entrata di Arcadia', ''),
(911, 'Passaggio Prato', 'Ti trovi in un prato iridescente, dove le erbe sembrano luccicare di una luce propria sotto le stelle. Senti che dovresti andare verso Nord', 'Il bagliore soave del prato ti accoglie ancora.', ''),
(912, 'Sud Foresta', 'Davanti a te si stende la Foresta degli Echi, un dedalo di antichi alberi muschiosi i cui tronchi sembrano susurrare antiche storie al vento. L''aria è intrisa di profumi terrestri e il terreno sotto i piedi cede dolcemente ad ogni tuo passo.', 'Sollevi lo sguardo verso i rami intrecciati, ormai familiarità con questi antichi sentinelli di legno', ''),
(913, 'Passaggio Foresta', 'Ti trovi in un sentiero di terra battuta che si snoda tra gli alberi della foresta. Il suono dei tuoi passi si perde tra il fruscio delle foglie e il canto degli uccelli. Dovresti andare verso Est', 'Passi di nuovo sulle foglie vicino alla Foresta', ''),
(914, 'Angolo Sud Caverna', 'Attraversi un sottobosco fitto dove il profumo del mirto pervade l''aria e il giochi di luce tra le foglie crea un mosaico di ombra e sole. Sembra esserci una caverna verso Nord.', 'Il delicato aroma di mirto ti saluta di nuovo', ''),
(916, 'Sud Caverna', 'Ti inoltri in un passaggio dove il muschio cresce in abbondanza, rivestendo ogni superficie di un manto vivido e soffice al tatto. Dinanzi a te la caverna che tanto cercavi', 'Sei di nuovo in mezzo al muschio', ''),
(918, 'Sud Cimitero', 'Ti trovi d''avanti al Cimitero del regno, potresti proseguire ad Ovest per il leggendario Tempio, o a Nord verso il cimitero tetro...', 'Sei di nuovo alle porte del Cimitero, a Nord entri nel Cimitero, ad Ovest si trova il Tempio e ad Est il Regno.', ''),
(919, 'Sud-Ovest Cimitero', 'Un sentiero coperto di aghi di pino e macchie di resina appiccicosa, diffonde nell''aria il profumo balsamico della conifera.', 'Ricordi che c''è un tempio a Nord-Ovest ed il Regno a Nord-Est.', ''),
(920, 'Angolo Tempio', 'Le pendici sono ricoperte da un tappeto multicolore di fiori selvatici, che ondeggiano al vento, quest''angolo forma un bivio naturale che porta a NORD al Tempio.', 'Quando i petali sono in fiore, il profumo dolce raggiunge persino le alte mura del Castello. Ti ricordi che a NORD c''è il Tempio, mentre ad EST puoi tornare verso la valle per tornare al Castello.', ''),
(922, 'Sud Fondo Tempio', 'Due imponenti formazioni rocciose si ergono all''entrata della valle, quasi come guardiani che proteggono la via per il Tempio.', 'Al secondo sguardo, si possono notare antiche incisioni sulle rocce, testimonianze di un tempo passato. Noti a NORD il Tempio, e ricordi che ad EST potresti tornare al Castello.', ''),
(923, 'Sud Tempio', 'D''avanti a te il maestoso Tempio del Custode, è un edicio antichissimo, forse più vecchio del Regno stesso, qui studiavano gli antichi custodi, protettori delle armonie e dei canti.', 'Ti trovi d''avanti al Tempio del Custode. E'' ancora incredibile riuscire ad essere qui!', '');

-- Regno di Arcadia
INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata, enterable)
VALUES
(101, 'Regno di Arcadia', 'Ti trovi nel regno di Arcadia, un luogo incantato dove la magia è palpabile nell''aria. Le strade sono lastricate di pietre bianche e le case sono fatte di legno e pietra. I cittadini sono gentili e sorridenti, e i bambini giocano felici per le strade. Il Regno è dominato da un castello maestoso, che si staglia contro il cielo azzurro. Il suono di una liuta si diffonde nell''aria, proveniente dalla bottega del liutaio ad ovest. Il regno è un luogo di pace e prosperità, dove la magia è parte integrante della vita quotidiana.', 'Sei di nuovo nel regno di Arcadia, i bambini giocano felici per le strade, i cittadini sono gentili e sorridenti, il castello si staglia contro il cielo azzurro.', '', 0),
(327, 'Porta Enigma Liuto', 'Hai aperto finalmente la porta della Caverna, vedi in lontantanza un pilastro enorme con al di sopra il Liuto Leggendario di “Arandel Markor", l''antico Custode.', 'Hai preso il liuto vero? Se si, forse si può tornare al Regno', '', 0),
(527, 'Porta Enigma Pentagramma', 'Hai aperto l''antico marchingegno, vedi in lontananza un pilastro con una teca, forse c''è qualcosa di importante lì...', 'Hai visto cosa c''era nella teca vero? Se si, potresti tornare al Regno', '', 0),
(107, 'Castello', 'Sei nel maestoso Castello di Arcadia, ma non è il momento di guardare la struttura, la minaccia del Drago incombe sempre più forte sul Regno, è il momento di agire, hai raccolto tanti oggetti leggendari, forse il momento di usarli!', 'Sei di nuovo nel castello, la minaccia del Drago è sempre più forte.', 'Hai vinto.', 0);

INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata)
VALUES
(102, 'Passaggio Case 1', 'Ti addentri in un piccolo vicolo, dove le case si stringono l''una contro l''altra, e vasai modellano argilla con mani sapienti sotto le tettoie dei loro laboratori. Vedi in lontananza una grande piazza.', 'La morbida rotazione dei torni dei vasai risuona ancora. Puoi uscire dal Regno a SUD, o a NORD c''è la piazza.', ''),
(103, 'Ingresso Piazza', 'Cammini in uno stretto tratturo che si apre improvvisamente nella vivace piazza del mercato, dove le voci dei mercanti si fondono in un coro di vita quotidiana.', 'Sei vicino alla piazza, a SUD puoi uscire dal Regno.', ''),
(104, 'Piazza Centrale', 'Ti trovi nella piazza del mercato, un luogo vivace e colorato dove i mercanti vendono le loro merci. Le bancarelle sono piene di frutta e verdura fresca, spezie esotiche e tessuti pregiati. Vedi un enorme figura scura nel cielo, vicino al Castello, senti un grande frastuono provenire da quella direzione. Al centro ci sono dei cartelli: \n ↗ NORD-EST Castello \n ↖ NORD-OVEST Liutaio \n ↙ SUD-OVEST Armaiolo.', 'Sei di nuovo nella piazza del mercato, i cartelli indicano: \n ↗ NORD-EST Castello \n ↖ NORD-OVEST Liutaio \n ↙ SUD-OVEST Armaiolo.', ''),
(105, 'Est Piazza', 'Lasciandoti alle spalle il brulicare della piazza dei mercanti, con i suoi banchi colorati e le chiamate dei venditori, ti avventuri lungo il Corso dei Cavalieri. Questa strada larga e maestosa è lastra di pietre levigate, testimoni di innumerevoli passaggi, e fiancheggiata da alte case nobiliari con i loro stemmi scolpiti sopra le porte e i balconi fioriti. Man mano che procedi, la via si inclina dolcemente verso l''alto, conducendoti alla maestosa silhouette del castello che si staglia contro il cielo.', 'Vedi verso OVEST la piazza dei mercanti e a NORD il Castello!', ''),
(106, 'Entrata Castello', 'D''avanti a te vedi il Castello del Re di Arcadia, una guardia ti ferma urlando "PASTORE VATTENE, UN DRAGO HA ATTACCATO IL CASTELLO, SOLO IL CUSTODE POTREBBE SALVARCI CON IL SUO LIUTO LEGGENDARIO E IL PENTAGRAMMA..."', 'Vedi di nuovo la guardia agitata... (forse potrebbe farti entrare con il liuto e il pentagramma che ha chiesto...)', '"RAGAZZO MA SEI IL NUOVO CUSTODE? PROVA A CALMARE IL DRAGO, AIUTA ARCADIA!" forse dovresti agire Luca, è il tuo momento.'),
(108, 'Ovest Piazza', 'Una corte silenziosa si nasconde dietro le abitazioni, un pozzo di pietra ne è il fulcro, circondato da abiti stesi ad asciugare e fioriere colorate sulle finestre. Vedi ad OVEST dei cartelli, potrebbero essere utili.', 'L''acqua del pozzo vecchio ti offre di nuovo il suo fresco benvenuto. Vedi ad EST la piazza.', ''),
(109, 'Cartelli', 'Ti trovi ad un bivio con dei cartelli, questi recitano: \n ↑ NORD Liutaio \n ↓ SUD Armaiolo \n → EST Piazza \n ↗ NORD-EST Castello', 'Leggendo i cartelli, vedi che: \n ↑ NORD Liutaio \n ↓ SUD Armaiolo \n → EST Piazza \n ↗ NORD-EST Castello', ''),
(110, 'Bottega del Liutaio', 'Ti trovi nella bottega del liutaio, un piccolo negozio pieno di strumenti musicali. Il liutaio è un uomo anziano con dei lunghi baffi bianchi, ti guarda con occhi saggi. (prova a parlarci)', 'Sei di nuovo nella bottega del liutaio, il liutaio ti guarda con occhi saggi e ti chiede se hai bisogno di uno dei suoi servigi.', ''),
(111, 'Via dei Fabbriferrai', 'Attraversi una strada stretta, fiancheggiata da botteghe di fabbriferrai, il battito dei martelli sull''incudine riecheggia tra le mura di pietra delle case. D''avanti a te c''è l''insegna di un fabbro, potrebbe vendere qualcosa di utile.', 'Il clangore familiare degli artigiani ti accoglie nuovamente.', ''),
(112, 'Bottega dell''Armaiolo', 'Ti trovi nella bottega dell''Armaiolo, d''avanti a te c''è un grosso fabbro con un''aria estremamente minacciosa, forse potresti parlarci..', 'Sei di nuovo dal Fabbro, fa veramente caldo qui dentro...', ''),

-- Foresta
(201, 'Ingresso Foresta', 'Senti delle urla sembrano lontane, ma non è chiara la provenienza...', 'Sei di nuovo all''ingresso, le urla sono molto lontane, ma sembrano provenire da nord-est', 'Sei di nuovo all''ingresso della foresta, ora sembra molto tranquilla'),
(202, 'Passaggio destro Foresta', 'Le urla sembrano più vicine, forse davanti a te...', 'Le urla sono ancora lontane, ma senti con chiarezza che provengono da nord-est', 'Sei vicino all''uscita, prova ad andare verso ovest'),
(203, 'Passaggio angolo sud Foresta', 'Le urla sembrano vicine, provengono da davanti a te!', 'Le urla sono sempre più vicine, sembrano provenire da nord', 'Sei sempre più vicino all''uscita, se non ricordi male dovrebbe essere ad ovest'),
(204, 'Passaggio avanti Foresta', 'Le urla sono nei dintorni, sembra provenire da destra', 'Sembrano vicine, provengono da destra', 'L''uscita dovrebbe essere subito a sud'), 
(205, 'Passaggio centrale Foresta', 'Le urla sono vicine, provengono da destra e rimbombano anche davanti a te', 'Le urla sono vicinissime, sembrano provenrie da nord-est', 'Sei al centro della foresta, l''uscita se non ricordi male è a sud-ovest'),
(206, 'Passaggio accanto Foresta', 'Le urla sono molto vicine, provenire da davanti a te!', 'Le urla sono vicinissime, sembrano provenire proprio da nord', 'Sei al lato della foresta, prova ad andare dall''altro lato per uscire'), 
(207, 'Passaggio angolo nord Foresta', 'Le urla sono alla tua destra', 'Le urla sono così vicine, provengono da destra!', 'Per uscire dovresti andare a sud, giusto?'), 
(208, 'Passaggio vicino Bardo', 'Le urla sono molto vicine alla tua destra!', 'Le urla sono accanto a te alla tua destra!', 'Per uscire potresti andare a sud'),
(209, 'Bardo', 'Incontri un bardo legato con delle corde ad un albero: "AIUTAMI, viandante! Mi hanno intrappolato..."', 'C''è un bardo legato ad un albero, sembra spaventato, dovresti aiutarlo non credi?', 'Ora che il bardo è libero, forse potresti tornare sul tuo percorso'),

-- Caverna del Liuto
(301, 'Ingresso Caverna', 'L''ingresso è ampio e estramemente freddo, con raggi di luce che filtrano attraverso, senti il gocciolio dalle stalattiti, forse dovresti sbrigarti', 'Noti ora i dettagli delle rocce intorno, lisciate dal tempo e dalle intemperie.', ''),
(302, 'Passaggio 1', 'Il corridoio si restringe, obbligandoti a procedere in fila indiana, da entrambi i lati la caverna è stretta, ma abbastanza larga per passare tranquillamente.', 'Ci sono rocce di tutti i colori, è una grotta estramemente colorata rispetto a quello che temevi!', ''),
(303, 'Passaggio 2', 'Il corridoio è un po'' più stretto, non vedi molto bene dove stai andando...', 'Sembra ci sia una piccola apertura in alto da cui proviene un filo di luce. Ti permette di capire che l''uscita è verso EST, ma verso OVEST sembra esserci un vicolo cieco', ''),
(304, 'Passaggio 3', 'Il corridoio è sempre più stretto, e senti la tua voce rimbombare più facilmente, non ti convince continuare ad OVEST', 'La tua voce rimbomba più facilmente, non ti convince continuare ad OVEST, meglio andare ad EST non credi?', ''),
(305, 'Vicolo Cieco 1', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(306, 'Passaggio 4', 'Piccole gocce d''acqua cadono dal soffitto, creando un suono costante.', ' Vedendo da vicino, scopri le piccole pozzanghere riflettenti create dalle gocce, con un po'' di luce riesci a vedere l''uscita ad OVEST, ma si può continuare nella caverna ad EST', ''),
(307, 'Passaggio 5', 'Sei in una camera piena di cristalli che rifrangono la luce creando giochi di colori. Ti senti un po'' confuso ma è piacevole vedere bene la stanza, c''è un''uscita ad EST', 'Con più attenzione, puoi vedere come i cristalli sembrino vibrare delicatamente. Ricordi che l''uscita è ad OVEST!', ''),
(308, 'Passaggio 6', 'Un ruscello serpeggiante attraversa il percorso, il suo suono pacifico ti accompagna. Sembra continuare il suo corso verso NORD.', 'Ci sono tanti piccoli pesci nel ruscello. Questo ti ricorda che l''uscita è verso OVEST.', ''),
(309, 'Passaggio 7', 'Ci sono radici antiche pendono dal soffitto, come tentacoli alla ricerca di un nutrimento sepolto. Sembra si possa andare solo a NORD.', 'Si notano dei fiori estremamente rari pendere dalle radici. Ricordi che l''uscita è verso SUD-OVEST.', ''),
(310, 'Passaggio 8', 'Le pareti lucide amplificano la debole luce, creando illusioni ottiche. Sei molto confuso sul dove andare, noti una rientranza ad OVEST, ma non sei sicuro.', 'Ogni tua mossa sembra ripetuta da ombre giocose. Andare a SUD potrebbe riportarti indietro.', ''),
(311, 'Vicolo Cieco 2', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(312, 'Passaggio 9', 'L''acustica particolare fa sì che persino un bisbiglio risuoni come un grido.', 'Se ti fermi ad ascoltare, potresti udire il segreto racconto del vento, o forse stai solo impazzendo.', ''),
(313, 'Passaggio 10', 'Ci sono diverse stalattiti formate in modo tale da emettere note in armonia quando colpite dall''acqua.', 'Una melodia sotterranea segreta si rivela a chi ha pazienza di ascoltare.', ''),
(314, 'Vicolo Cieco 3', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(315, 'Passaggio 11', 'Un tratto così basso da dover procedere carponi, attento alla testa!', 'Sul soffitto sembrano disegnati geroglifici molto antichi.', ''),
(316, 'Passaggio 12', 'Vedi lampi di luce che proiettano ombre in continuo movimento sulle pareti grezze.', 'Sembri quasi in grado di discernere le figure ripetute e prevedere la danza selvaggia delle luci, ma non perderci la testa, l''uscita è verso SUD', ''),
(317, 'Passaggio 13', 'Una cascata si ferma a mezz''aria come congelata da una magia antica. Non avevi mai visto nulla del genere.', 'L''acqua della cascata gocciola in realtà molto lentamente, quasi sospesa.', ''),
(318, 'Passaggio 14', 'Un soffio costante di vento percorre questo passaggio, inspiegabilmente sempre fresco.', 'Sembrano esserci piccole aperture attraverso cui l''aria esterna s''infiltra. Il vento conduce verso EST, dovrebbe essere l''uscita.', ''),
(319, 'Passaggio 15', 'Le pareti sono così vicine da poter lasciare un''impronta di te sul muro umido.', 'Noti vecchie impronte di mani, come testimoni mute della tua stessa esplorazione. Per tornare indietro ad EST!', ''),
(320, 'Passaggio 16', 'Qui, le rocce sembrano prendere forme familiari, animali, volti, storie scolpite dal caso. La confusione è tanta!', 'La tua immaginazione si accende, le rocce sembrano aver preso vita diversa, sembravano animali, ora sono mostri! Forse dovresti tornare indietro proseguendo a NORD!', ''),
(321, 'Passaggio 17', 'Le gocce d''acqua che cadono dai picchi creano un ritmo ipnotico sulla superficie sottostante.', 'La cadenza delle gocce è costante, quasi un metronomo naturale che misura il flusso del tempo. Ti ricorda che l''uscita è verso NORD.', ''),
(322, 'Passaggio 18', 'Questo passaggio è divisa perfettamente a metà da una crepa nel terreno, distingui bene le due metà e sai che devi andare verso SUD per continuare.', 'La simmetria è così perfetta che ti domandi se non sia opera di mani ignote piuttosto che della natura. Per uscire dovresti andare verso SUD, giusto?', ''),
(323, 'Passaggio 19', 'Fa molto caldo qui, c''è molto vapore, ma riesci a vedere un po'' la strada...', 'Sudi ogni volta che passi da qui, per uscire prova ad andare verso NORD.', ''),
(324, 'Passaggio 20', 'L''angolo di un passaggio è cosparso di frammenti brillanti di minerali rotti, scintillanti come stelle su un cielo di roccia. Sembra ci sia qualcosa di importante in fondo', 'I minerali splendono sempre di più ogni volta che passi.', ''),
(325, 'Passaggio 21', 'Le pareti qui sono ricoperte di incisioni antiche, il cui significato esatto rimane un mistero. Tuttavia, sembri distinguere diverse note musicali, quasi come se gli antichi Custodi fossero passati di qui.', 'Le incisioni sembra un po'' più chiare del solito, ma comunque indecifrabili nella suo interezza. Per uscire prova ad andare verso EST', ''),
(326, 'Passaggio 22', 'D''avanti a te si pone un''enorme porta con uno strano meccanismo, un''effige scritta a mano recita "SOLO I CUSTODI POSSONO ENTRARE, LA BALLATA TIPICA DEL REGNO E'' TRADIZIONE.", comprendi qualcosa di ciò che c''è scritto, ma non è chiarissimo.', 'Sei di nuovo d''avanti alla porta, per uscire il percorso è tortuoso, prova ad andare a SUD per ora.', ''),
(328, 'Passaggio Enigma Liuto', 'Dopo la porta, d''avanti c''è un grande pilastro di marmo, con un solo riflesso enorme, come un''antica chiesa. Noti qualcosa al centro della stanza, prova a proseguire.', 'Le rocce tutto intorno hanno degli strani geroglifici con interi pentagrammi musicali, vorresti suonarli, ma forse non è il momento!', ''),
(329, 'Liuto Leggendario', 'Al centro della stanza si trova un piedistallo in marmo, con sopra il Liuto leggendario dell''Antico Custode, le leggende narrano che grazie ad esso i draghi siano stati scacciati dal regno di Arcadia, senza tornare mai più.', 'Ti trovi nella stanza con il piedistallo per il Liuto Leggendario.', ''),

-- Cimitero
(401, 'Ingresso Cimitero', 'Apri il piccolo cancello di ferro e ti ritrovi immediatamente nel Cimitero, il luogo più oscuro di tutto il regno. Un sentiero di ciottoli, costeggiato da alte siepi scure ormai prive di foglie, ti invita ad avanzare. Ai lati del viale, le statue e le croci si perdono nella nebbia, e una brezza gelida ti fa stringere il mantello più vicino.', 'Sei di nuovo all''ingresso, rimane sempre quell''aria tenebre, forse è arrivato il momento di andartene.', ''),
(402, 'Passaggio Tombe 1-2', 'Ci sono tante tombe ai lati del percorso, è tutto così tetro.', 'Il passaggio è sempre tetro', ''),
(403, 'Tomba Generica 1', 'Sembra la tomba di un abitante di Arcadia importante, ma non si legge più il nome sull''effige', 'La tomba è rimasta la stessa, effettivamente i morti non si muovono.', ''),
(404, 'Tomba Generica 2', 'Sembra la tomba di un abitante di Arcadia importante, ma non si legge più il nome sull''effige', 'La tomba è rimasta la stessa, effettivamente i morti non si muovono.', ''),
(405, 'Passaggio Tombe Custode-3', 'Ci sono tantissime tombe ai lati', 'Sei al centro del cimitero, l''aria è sempre più tetra', ''),
(406, 'Tomba del Custode', 'E'' una tomba di marmo bianco puro, incastonata nel terreno come uno scrigno prezioso. Sulla sua superficie, finemente scolpita, si può vedere un pentagramma con note incise a rilievo che compongono una melodia arcana, conosciuta solo ai più saggi. Il nome scritto sopra di essa è “Arandel Markor” il leggendario Custode del tempio, sembra ci sia una sorta di marchingegno accanto alla tomba, quasi come a difesa di un piccolo oggetto.', 'Senti sempre un''aura potentissima intorno a questa tomba', 'Ora che non c''è più la chiave forse è meglio andare, qualcuno potrebbe accorgersene'),
(407, 'Tomba Generica 3', 'E'' la tomba di un cittadino di Arcadia, forse qualche personaggio importante della borghesia.', 'Una tomba VERAMENTE troppo sfarzosa. Forse non avrai mai una tomba così...', ''),
(408, 'Passaggio Tombe 4-MS-5', 'Anche qui ci sono tombe ovunque, tuttavia si sente un odore di fumo, come se qualcuno stesse fumando tabacco, tossisci ma riesci comunque ad andare avanti', 'L''odore si fa sempre più forte', ''),
(409, 'Tomba Generica 4', 'E'' la tomba di un cittadino di Arcadia, forse qualche personaggio importante della borghesia.', 'Una tomba VERAMENTE troppo sfarzosa. Forse non avrai mai una tomba così...', ''),
(410, 'Mr. Scaramboola', 'Vedi uno scheletro seduto su una tomba, sei terrorizzato, inizi a scappare ma appena fai il primo passo si alza ed inizia a parlare “What''s up Lil Bro?? Come stai? E'' da un bel po'' di tempo che non vedo qualcuno passare dallo spooky cemetery, sono Mr. Scaramboola un genio dal futuro”, non avevi mai visto una cosa del genere, ma sembrava troppo simpatico per andartene. Aveva un accento molto particolare, sicuramente proveniva da qualche terra lontana… non fai in tempo a rispondere che ti inizia a fare domande su cose estremamente complicate di cui non sai assolutamente nulla.', 'È di nuovo Mr. Scaramboola, lui ti vede e ti chiede: ''HERE WE GO AGAIN LET''S PLAY''', ''),
(411, 'Tomba Generica 5', 'E'' la tomba del vecchio re di Arcadia, è sicuramente la tomba più maestosa dell''intero cimitero', 'Senti un senso di orgoglio ogni volta che passi di qui, il vecchio Re ha garantito un terreno alla tua famiglia, lo stesso terreno in cui vivi ora.', ''),

-- Tempio del Custode
(501, 'Ingresso Tempio', 'Ti trovi all''ingresso del Tempio, un luogo sacro e misterioso dove la magia è palpabile nell''aria. Le mura sono ricoperte di simboli e geroglifici, e il suono di un flauto si diffonde nell''aria, proveniente dall''interno del tempio. Il tempio è un luogo di pace e spiritualità, dove la magia è parte integrante della vita quotidiana.', 'Sei di nuovo davanti al tempio, il suono del flauto si diffonde nell''aria, proveniente dall''interno del tempio.', ''),
(502, 'Passaggio 1', 'Entri in un''enorme arcata con incise note musicali, sembra sussurrare antichi inni al tocco del vento.', 'Ti trovi di nuovo d''avanti all''arcata con le incisioni. Sei praticamente all''uscita', ''),
(503, 'Passaggio 2', 'Una lunga navata con pilastri in cui sembrano riecheggiare delle melodie cantate in coro dai monaci.', 'Senti le voci dei monaci riecheggiare sempre più forte, forse stai impazzendo...', ''),
(504, 'Passaggio 3', 'Sembra l''ingresso di un''aula con resti di organi di legno, i cui tubi ormai danneggiati aspirano a ricreare suoni dimenticati.', 'Sei di nuovo nell''aula degli strumenti dimenticati, forse avresti potuto imparare qualcosa tempo fa qui...', ''),
(505, 'Vicolo Cieco 1', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(506, 'Passaggio 4', 'Sei in una stanza tondeggiante, qui i monaci copiavano partiture, ogni foglio risonante della loro devozione. Una stanza molto buia, piena di rimanenze di cera.', 'Sei di nuovo nello Scriptorium, sei vicino all''uscita.', ''),
(507, 'Passaggio 5', 'La stanza sembra quasi un anfiteatro, ambienti dedicati alla composizione, dove melodie sacre prendevano forma sotto le mani ispirate.', 'L''anfiteatro è molto antico, ma risulta sempre essere un belvedere...', ''),
(508, 'Passaggio 6', 'Al centro della stanza un altare dove i monaci accordavano i loro strumenti prima delle funzioni religiose. Ci sono diverse statue ai lati che raffigurano antiche divinità con diversi strumenti musicali.', 'Sei di nuovo d''avanti all''altare. L''uscita dovrebbe essere verso OVEST', ''),
(509, 'Passaggio 7', 'Sembra una mensa, un luogo dove, un tempo, le percussioni scandivano il tempo dei pasti collettivi.', 'Sei di nuovo alla mensa comune.', ''),
(510, 'Passaggio 8', 'La stanza è piena di incavi dove anticamente riposavano liuti e flauti, tuttora adornati di simboli musicali.', 'Sei di nuovo nella stanza con gli incavi, noti rimanenze di strumenti antichi.', ''),
(511, 'Passaggio 9', 'I soffitti qui sono curvi progettati per propagare ogni suono, evocando una sensazione di unità sonora.', 'Sei di nuovo nella stanza con i soffitti curvi, la tua voce rimbomba come un''orchestra.', ''),
(512, 'Passaggio 10', 'Sequenze di colonne che sembrano scandire i battiti musicali dei canti dei monaci.', 'Le colonne sembra quasi salutarti con il proprio eco.', ''),
(513, 'Passaggio 11', 'La stanza preparatoria per i servizi religiosi, ancora impregnata del battito dei tamburi cerimoniali.', 'Sei di nuovo nella sala per la preparazione dei riti religiosi, il rimbombo dei tamburi ti fa sentire pieno di te.', ''),
(514, 'Passaggio 12', 'Una sala con una fontana al centro ormai asciutta, i cui zampilli seguivano ritmi che ricordavano musiche sacre.', 'La fontana porta in te ricordi felici delle mattine nel Regno in Piazza.', ''),
(515, 'Passaggio 13', 'Tanti mosaici sul pavimento con motivi che sembrano note musicali, ancora calpestabili con riverenza', 'La voglia di ballare ti assale vedendo questo pavimento..', ''),
(516, 'Vicolo Cieco 2', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(517, 'Passaggio 14', 'Una piccola cappella dove il silenzio è così profondo che affina l''udito fino a percepire un lieve vibrare delle pietre.', 'Qui, il silenzio parla. Stai per impazzire forse...', ''),
(518, 'Vicolo Cieco 3', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(519, 'Passaggio 15', 'Sembra uno spazio dove gli studenti sperimentavano con strumenti vari, imparando l''arte di tessere insieme suoni diversi in una tessitura armonica.', 'Ti risulta quasi familiare, i racconti di tuo nonno raffiorano.', ''),
(520, 'Passaggio 16', 'Una stanza progettata con un''acustica perfetta, questa sala permette agli studenti di ascoltare e affinare la propria voce, da solisti o in coro.', 'Ti rendi conto che avresti sempre voluto studiare qui...', ''),
(521, 'Passaggio 17', 'La sala sembra progettata per studiare e praticare la polifonia e la contrappuntistica, con la guida di esperti in queste tecniche secolari.', 'Ti ricordi che tuo nonno era un maestro delle arti musicali, forse era un maestro qui...', ''),
(522, 'Passaggio 18', 'Sembra un angolo dedicato agli strumenti ad arco, dalla viola al contrabbasso, un luogo di pratica intensiva e di scoperta.', 'Forse qui potresti trovare un liuto antico! O forse no...', ''),
(523, 'Vicolo Cieco 4', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(524, 'Passaggio 19', 'Un ambiente dove si studiavano le antiche forme del canto liturgico, risuonanti tra mura che hanno udito le voci di generazioni passate.', 'Il nonno ti raccontava sempre dei canti liturgici dei monaci, forse è stato qui tempo fa!', ''),
(525, 'Vicolo Cieco 5', 'E'' un vicolo cieco, dove ogni suono genera un eco sorprendentemente chiaro.', 'Ti sei perso, sei di nuovo in un vicolo cieco...', ''),
(526, 'Passaggio 20', 'Un ampio salone dove i monaci si radunavano in occasione di festività, intonando inni e cantici in onore delle tradizioni passate. L''unica particolarità è una porta verso OVEST, forse lì dietro c''è cosa cerchi!', 'Sei di nuovo nell''ampio salone dei festeggiamenti.', ''),
(528, 'Passaggio Enigma Pentagramma', 'L''abitacolo privato dove un tempo viveva il Custode delle Armonie, rifugio di studio e contemplazione musicale. Senti un''aura molto potente in questa stanza.', 'Sei di nuovo nell''abitacolo privato del Custode.', ''),
(529, 'Pilastro Pentagramma Armonico', 'Un enorme stanza con al centro una teca, sembra quasi che il Custode mettesse qui l''antica Pergamena in grado di calmare le anime più oscure, si dice che grazie ad essa i Draghi siano fuggiti per sempre da Arcadia.', 'Sei di nuovo nella stanza della Pergamena.', '');

INSERT INTO ConnessioniCaselle (casella_id1, casella_id2, direzione) VALUES
(901, 902, 'n'), (902, 903, 'n'), (903, 905, 'n'), (904, 907, 'n'), (905, 908, 'n'),
(908, 101, 'n'), (918, 401, 'n'), (920, 922, 'n'), (922, 923, 'n'), (923, 501, 'n'), 
(906, 909, 'n'), (911, 912, 'n'), (912, 201, 'n'), (914, 916, 'n'), (916, 301, 'n'), 
(101, 102, 'n'), (102, 103, 'n'), (103, 104, 'n'), (105, 106, 'n'), (106, 107, 'n'), 
(112, 111, 'n'), (111, 109, 'n'), (109, 110, 'n'), (201, 204, 'n'), (204, 207, 'n'), 
(202, 205, 'n'), (205, 208, 'n'), (203, 206, 'n'), (206, 209, 'n'), (301, 302, 'n'), 
(308, 309, 'n'), (309, 310, 'n'), (310, 312, 'n'), (312, 313, 'n'), (313, 315, 'n'), 
(315, 316, 'n'), (323, 322, 'n'), (322, 321, 'n'), (321, 320, 'n'), (320, 319, 'n'), 
(325, 326, 'n'), (326, 327, 'n'), (327, 328, 'n'), (328, 329, 'n'), (401, 402, 'n'), 
(402, 405, 'n'), (405, 408, 'n'), (408, 411, 'n'), (403, 406, 'n'), (406, 409, 'n'), 
(404, 407, 'n'), (407, 410, 'n'), (501, 502, 'n'), (508, 509, 'n'), (509, 510, 'n'), 
(510, 511, 'n'), (511, 512, 'n'), (513, 514, 'n'), (514, 515, 'n'), (522, 521, 'n'), 
(521, 520, 'n'), (527, 528, 'n'), (528, 529, 'n'), (920, 919, 'e'), (919, 918, 'e'), 
(918, 907, 'e'), (907, 908, 'e'), (908, 909, 'e'), (909, 911, 'e'), (904, 905, 'e'), 
(905, 906, 'e'), (912, 913, 'e'), (913, 914, 'e'), (109, 108, 'e'), (108, 104, 'e'), 
(104, 105, 'e'), (201, 202, 'e'), (202, 203, 'e'), (204, 205, 'e'), (205, 206, 'e'), 
(207, 208, 'e'), (208, 209, 'e'), (305, 304, 'e'), (304, 303, 'e'), (303, 302, 'e'), 
(302, 306, 'e'), (306, 307, 'e'), (307, 308, 'e'), (311, 310, 'e'), (314, 313, 'e'), 
(319, 318, 'e'), (318, 317, 'e'), (317, 316, 'e'), (325, 324, 'e'), (324, 323, 'e'),
(403, 402, 'e'), (402, 404, 'e'), (406, 405, 'e'), (405, 407, 'e'), (409, 408, 'e'), 
(408, 410, 'e'), (505, 504, 'e'), (504, 503, 'e'), (503, 502, 'e'), (502, 506, 'e'), 
(506, 507, 'e'), (507, 508, 'e'), (518, 517, 'e'), (517, 515, 'e'), (525, 524, 'e'), 
(524, 522, 'e'), (522, 523, 'e'), (527, 526, 'e'), (526, 520, 'e'), (520, 519, 'e'), 
(519, 513, 'e'), (513, 512, 'e');

-- Inserimento Dialoghi
INSERT INTO Dialoghi(id, priorita, dialogoIniziale, rispostaSeCorretta, rispostaSeErrata, rispostaCorretta)
VALUES
(903, 0, '"Ei pastorello, avresti una monetina per un mendicante come me?"', '"Grazie pastore, ti benedicano tutti gli Dei!"', '"Sei il peggio del peggio del Regno, ti maledico!!"', 'si'),
(908, 0, 'La guardia ti osserva con occhi penetranti, poi, con una voce che rimbomba sotto il casco, ti dice, "Ah, un umile pastore cerca l''ingresso ad Arcadia. Prima di varcare queste soglie, dimmi, riesci a risolvere il mio enigma?", fai un cenno con la testa e l''enigma che ti pone ti sembra quasi impossibile: “E'' una combinazione di tre cifre, ma io ti dirò solo tre parole “Pecore, Rami, Pietra”', 'La guardia ti guarda orgogliosa, “Non era così difficile visto? Prego, entra pure ad Arcadia, ma ricorda, non dare problemi o ti manderò in galera personalmente.”', 'La guardia ti guarda amareggiata, e ti risponde “Mi aspettavo di meglio da un contadino, sarà per la prossima volta!”', '231'),
(908, 1, 'Sei tornato davanti a quelle enormi porte, la guardia rimane lì a guardarti e ti chiede “Vuoi entrare o rimani lì a guardare?”', '', '', ''),
(110, 0, 'Ti guarda e ti chiede "Hai bisogno di riparare quel vecchio Liuto vero? Per una moneta d''oro verrà fuori come nuovo, vorresti lasciarlo?"', '"Certo figliolo, inizio subito...", mentre ripara il Liuto, inizia a raccontarti dello strumento del vecchio Custode, ormai venuto a mancare, il Liuto Leggendario, le leggende narrano che si trova in una grotta a nord-est del Regno.', '"Come vuoi figliolo, non ti obbligherò! Torna quando vuoi!"', 'si'),
(110, 1, '"Sei andato alla grotta alla fine? Forse potresti aiutare il Regno con quel Liuto"', '', '', ''),
(112, 0, 'L''Armaiolo ti guarda con aria sospettosa, "Senti pastore, ho questo Coltello in sconto per una moneta, lo vuoi? Puoi farci quello che vuoi, basta che non fai il mio nome d''avanti alle guardie"', 'L''Armaiolo ti guarda con aria soddisfatta, "Hai fatto un affare, pastore, ti auguro di non doverlo mai usare!"', 'L''Armaiolo ti guarda con aria sospettosa, "Non mi sembri un tipo affidabile, torna quando avrai più soldi!"', 'si'),
(112, 1, 'L''Armaiolo ti guarda contento, "Ciao pastore, come va con quel vecchio Coltello? Spero tu non abbia ucciso nessuno AHAHAAHAHAHAH... vero?"', '', '', ''),
(410, 0, '"What''s up lil'' kid?? Come stai? Mr. Scaramboola qui, come here and PLAY... HIP HIP ARRAY! Dimmi un po''..."', 'Mr. Scaramboola è proprio contento: "COME ON KID, YOU DID IT, sei pazzesco!"', 'Mr. Scaramboola ti guarda con aria delusa, "Non hai capito nulla, vero? Do not think about it too much little kid, la prossima volta sarai più preparato!"', '');

-- Inserimento Oggetti
INSERT INTO Oggetto(id, nome, descrizione, quantita, pickable, visible)
VALUES
(1, 'Moneta d''Oro', 'La valuta corrente del regno di Arcadia, sopra ci sono incise delle strane rune.', 3, 1, 1),
(2, 'Coltello', 'Un coltello di ferro, decisamente tagliente.', 1, 0, 0),
(3, 'Ballata Montone', 'Un foglio con scritto il tipico ballo del regno, DUE passi AVANTI, DUE passi INDIETRO, UN passo a SINISTRA, UN passo a DESTRA.', 1, 0, 0),
(4, 'Liuto Leggendario', 'Il leggendario Liuto d''argento, precedentemente posseduto dall''antico Custode.', 1, 1, 1),
(5, 'Chiave Tempio', 'Una chiave d''oro, molto pesante, ricorda la forma di una chiave di violino', 1, 0, 1),
(6, 'Pentagramma Armonico', 'Un pentagramma scritto su una seta purissima, incise le note musicali della leggendaria Armonia del Custode', 1, 1, 1);


-- Inserimento Alias Oggetti
INSERT INTO Alias(id, alias)
VALUES
(1, 'Moneta'),
(1, 'Oro'),
(3, 'Ballata'),
(4, 'Liuto'),
(5, 'Chiave'),
(6, 'Pentagramma');

-- Inserimento Oggetti Casella
INSERT INTO Casella_Oggetto(casella_id, oggetto_id, quantita)
VALUES
(901, 1, 4), (112, 2, 1), (209, 3, 1), (329, 4, 1), (406, 5, 1), (529, 6, 1);