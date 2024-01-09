import extensions.CSVFile;

class fightquiz extends Program {
    //fonctions pratiques
    int idSup (String[] tab, int id) { //donne l'id supérieur, ou 0 si cet id est trop grand pour le tableau entré en paramètre
        if (id == length(tab)-1) {
            return(0);
        }
        return(id+1);
    }
    String readFile (String dir) { //lit le contenu d'un fichier
        extensions.File file = newFile(dir);
        String res = "";
        for (int id = 0; id < 22; id += 1) {
            res += readLine(file) + '\n';
        }
        res += readLine(file);
        return(res);
    }
    String readFileAlt (String fileN) { //version alternative de la fonction précédente, nécéssaire pour l'affichage des questions
        String dir = "ressources/img/question/"+fileN;
        extensions.File file = newFile(dir);
        String res = "";
        for (int id = 0; id < nbLignes(dir)-2; id += 1) {
            res += readLine(file) + '\n';
        }
        res += readLine(file);
        return(res);
    }
    int nbLignes(String cheminFichier){ //donne le nombre de lignes d'un document texte
        extensions.File fich = newFile(cheminFichier);
        int count = 1;
        boolean fini = false;
        String line;
        while (!fini) {
            line = readLine(fich);
            if (line != null) {
                count += 1;
            } else {
                fini = true;
            }
        }
        return(count);
    }
    String[] initImg (String path) { //assigne chaque "frame" d'une animation à un tableau de Strings
        String[] files = getAllFilesFromDirectory("ressources/img/"+path);
        String[] tab = new String[length(files)];
        for (int id = 0; id < length(tab); id += 1) {
            tab[id] = readFile("ressources/img/"+path+"/"+id+".txt");
        }
        return(tab);
    }
    String viesAffich (int vies){ //formattage de l'affichage des vies
        String aff = "";
        int compteur = 0;
        while (compteur < vies) {
            aff += "♥︎ ";
            compteur += 1;
        }
        while (compteur < 5) {
            aff += "♡ ";
            compteur += 1;
        }
        return(substring(aff,0,length(aff)-1));
    }
    void showQuestion(int vies, Question ques, int score) { //formattage de l'affichage de la question
        print(readFileAlt("B4S.txt")+scoreFormat(score,false)+readFileAlt("B4L.txt")+viesAffich(vies)+readFileAlt("B4Q.txt"));
        int spaces = (78 - length(ques.ques))/2;
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print(ques.ques);
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        println(readFileAlt("AFQ.txt"));
    }
    void showRéponses(Question ques) { //formattage de l'affichage des réponses
        print(readFileAlt("B4R.txt"));
        int spaces = (30 - length(ques.rep1.rep))/2;
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print("A:"+ques.rep1.rep);
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print("#      #");
        spaces = (30 - length(ques.rep2.rep))/2;
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print("B:"+ques.rep2.rep);
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print(readFileAlt("BTR.txt"));
        spaces = (30 - length(ques.rep3.rep))/2;
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print("C:"+ques.rep3.rep);
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print("#      #");
        spaces = (30 - length(ques.rep4.rep))/2;
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print("D:"+ques.rep4.rep);
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        println(readFileAlt("AFR.txt"));
    }
    void showBattle(String mat, String battleBg) { //formattage de l'affichage du combat
        extensions.File main = newFile("ressources/img/question/mnstr/"+battleBg+".txt");
        extensions.File mnstr = newFile("ressources/img/question/mnstr/"+mat+".txt");
        println(readLine(main));
        for (int id = 0; id < 7; id += 1) {
        print(readLine(main));
        print(readLine(mnstr));
        println(readLine(main));
        }
        println(readLine(main));
        println(readLine(main));
    }
    Réponse[] shuffleRep(Réponse[] tab) { //mélange des réponses données
        Réponse[] shuffledTab = new Réponse[]{initRep("none","false"),initRep("none","false"),initRep("none","false"),initRep("none","false")};
        for (int id = 0; id < 4; id += 1) {
            int randId = (int) (random() * (double) 4);
            while (!equals(shuffledTab[randId].rep,"none")) {
                randId = (int) (random() * (double) 4);
            }
            shuffledTab[randId] = tab[id];
        }
        return shuffledTab;
    }
    Réponse initRep(String repStr, String bonneRep) { //initialisation d'une réponse
        Réponse rep = new Réponse();
        rep.rep = repStr;
        if (length(rep.rep) % 2 != 0) {
            rep.rep += " ";
        }
        if (equals(bonneRep, "true")) {
            rep.bonneRep = true;
        } else {
            rep.bonneRep = false;
        }
        return rep;
    }
    Question initQues(String quesStr, Réponse rep1, Réponse rep2, Réponse rep3, Réponse rep4) { //initialisation d'une question
        Question ques = new Question();
        ques.ques = quesStr;
        if (length(ques.ques) % 2 != 0) {
            ques.ques += " ";
        }
        ques.rep1 = rep1;
        ques.rep2 = rep2;
        ques.rep3 = rep3;
        ques.rep4 = rep4;
        ques.dejaPasse = false;
        return ques;
    }
    Matière initMat(String matStr, Question[] lstQues) { //initialisation d'une matière
        Matière mat = new Matière();
        mat.mat = matStr;
        mat.questions = lstQues;
        return mat;
    }
    Matière[] loadData() { //chargement des questions/réponses
        CSVFile csvMatières = loadCSV("ressources/data/matières.csv");
        int nbMat = rowCount(csvMatières) - 1;
        Matière[] matières = new Matière[nbMat];
        for (int idMat = 1; idMat <= nbMat; idMat += 1) {
            CSVFile csvQuestions = loadCSV("ressources/data/"+getCell(csvMatières, idMat, 0)+"/questions.csv");
            int nbQues = rowCount(csvQuestions) - 1;
            Question[] questions = new Question[nbQues];
            for (int idQues = 1; idQues <= nbQues; idQues += 1) {
                CSVFile csvRep = loadCSV("ressources/data/"+getCell(csvMatières, idMat, 0)+"/"+getCell(csvQuestions, idQues, 1)+".csv");
                Réponse rep1 = initRep(getCell(csvRep, 1, 0), getCell(csvRep, 1, 1));
                Réponse rep2 = initRep(getCell(csvRep, 2, 0), getCell(csvRep, 2, 1));
                Réponse rep3 = initRep(getCell(csvRep, 3, 0), getCell(csvRep, 3, 1));
                Réponse rep4 = initRep(getCell(csvRep, 4, 0), getCell(csvRep, 4, 1));
                Réponse[] reps = new Réponse[]{rep1,rep2,rep3,rep4};
                reps = shuffleRep(reps);
                questions[idQues-1] = initQues(getCell(csvQuestions, idQues, 0), reps[0], reps[1], reps[2], reps[3]);
            }
            matières[idMat-1] = initMat(getCell(csvMatières, idMat, 0), questions);
        }
        return matières;
    }
    Question chargerQuestion(Matière[] tab, int mat) { //choix aléatoire d'une question dans une matière donnée
        int idQues = (int) (random() * (double) length(tab[mat].questions));
        while (tab[mat].questions[idQues].dejaPasse == true) {
            idQues = (int) (random() * (double) length(tab[mat].questions));
        }
        tab[mat].questions[idQues].dejaPasse = true;
        return(tab[mat].questions[idQues]);
    }
    boolean entrerRep(int vies, String mat, Question ques, int score, String battleBg) { //gère l'entrée d'une réponse de façon correcte
        String rep = "  ";
        boolean repOK = false;
        while (!repOK) {
            rep = toUpperCase(readString());
            if (length(rep) != 1) {
                affichQuestion(vies, mat, ques, score, battleBg);
            } else if (charAt(rep,0) > 'D' || charAt(rep,0) < 'A') {
                affichQuestion(vies, mat, ques, score, battleBg);
            } else {
                repOK = true;
            }
        }
        int idRep = charAt(rep,0) - 'A';
        if (idRep == 0) {
            return(ques.rep1.bonneRep);
        }
        if (idRep == 1) {
            return(ques.rep2.bonneRep);
        }
        if (idRep == 2) {
            return(ques.rep3.bonneRep);
        }
        return(ques.rep4.bonneRep);
    }
    String scoreFormat (int score, boolean battu) { //formattage de l'affichage du score
        String ScoreS = "" + score;
        while (length(ScoreS) < 8) {
            ScoreS = "0" + ScoreS;
        }
        if (battu) {
            return ("⭐"+ScoreS);
        }
        return("  "+ScoreS);
    }
    void affichScoreBattle(int prevScore, int newScore, int frame, String battleBg) { //formattage de l'affichage du nouveau score
        String prevScoreS = scoreFormat(prevScore,false);
        String newScoreS = scoreFormat(newScore,false);
        extensions.File file = newFile("ressources/img/question/mnstr/score/"+battleBg+"/"+frame+".txt");
        println(readLine(file));
        println(readLine(file));
        println(readLine(file));
        println(readLine(file));
        println(readLine(file));
        print(readLine(file));

        String score = prevScoreS+" -> "+newScoreS;
        int spaces = (59)/2;
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        print(score);
        for (int id = 0; id < spaces; id += 1) {
            print(" ");
        }
        for (int id = 0; id < 18; id += 1) {
            println(readLine(file));
        }
    }
    void affichAnimFromCSV(CSVFile fileList, int imgId) { //affichage d'une animation dans une cinématique
        println(getCell(fileList, imgId, 0));
        String[] imgData = initImg("cinematiques/"+getCell(fileList, imgId, 0));
        if (equals(getCell(fileList, imgId, 3),"false")) {
            println(imgData[0]);
            for (int id = 1; id < stringToInt(getCell(fileList, imgId, 1)); id += 1) {
                delay(stringToInt(getCell(fileList, imgId, 2)));
                println(imgData[id]);
            }
        } else {
            double endTime = getTime() + stringToInt(getCell(fileList, imgId, 4));
            int idImg = 0;
            println(imgData[idImg]);
            while (getTime() < endTime) {
                idImg = idSup(imgData, idImg);
                delay(stringToInt(getCell(fileList, imgId, 2)));
                println(imgData[idImg]);
            }
        }
        delay(stringToInt(getCell(fileList, imgId, 5))*1000);
    }
    int score (double startTime, double answerTime) { //détermination du score
        double timeDiff = answerTime - startTime;
        if (timeDiff < 15000) {
            int score = 1000;
            while (timeDiff > 3000) {
                timeDiff -= 3000;
                score -= 100;
            }
            return score;
        }
        if (timeDiff > 20000) {
            return 250;
        }
        return 500;
    }
    Score initScore(int score, String nom, String battu) { //initialisation d'un score
        Score sc = new Score();
        if (score > 99999999) {
            sc.score = 99999999;
        } else {
            sc.score = score;
        }
        sc.nom = nom;
        if (equals(battu,"true")) {
            sc.battu = true;
        } else {
            sc.battu = false;
        }
        return sc;
    }
    Score[] loadScores() { //chargement des scores
        CSVFile csvScores = loadCSV("ressources/data/score.csv");
        Score[] tab = new Score[6];
        for (int id = 1; id <= 6; id += 1) {
            tab[id-1] = initScore(stringToInt(getCell(csvScores,id,0)),getCell(csvScores,id,1),getCell(csvScores,id,2));
        }
        sortScores(tab);
        return tab;
    }
    void sortScores(Score[] tab) { //tri des scores
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int id = 0; id < 5; id += 1) {
                if (tab[id].score < tab[id+1].score) {
                    Score temp = tab[id];
                    tab[id] = tab[id+1];
                    tab[id+1] = temp;
                    changed = true;
                }
            }
        }
    }
    int getLowestScore(Score[] tab) { //donne le score le plus bas
        int low = tab[0].score;
        for (int id = 1; id < 6; id += 1) {
            if (tab[id].score < low) {
                low = tab[id].score;
            }
        }
        return low;
    }
    int getLowestScoreId(Score[] tab) { //donne l'id du score le plus bas
        int low = tab[0].score;
        int finalId = -1;
        for (int id = 1; id < 6; id += 1) {
            if (tab[id].score < low) {
                low = tab[id].score;
                finalId = id;
            }
        }
        return finalId;
    }
    String nameFormat(String name) { //formattage de l'affichage d'un pseudo
        String newName = name;
        while (length(newName) < 6) {
            newName += " ";
        }
        return newName;
    }
    void affichScore(int score, Score[] scores, boolean battu) { //formattage de l'affichage de l'écran des scores
        extensions.File file = newFile("ressources/img/scoreMenu/noHiScore.txt");
        for (int id = 0; id < 6; id += 1) {
            println(readLine(file));
        }
        for (int id = 0; id < 3; id += 1) {
            println(" #        " + (id+1) + " - " + scores[id].nom + " : " + scoreFormat(scores[id].score, scores[id].battu) + "              " + (id+4) + " - " + scores[id+3].nom + " : " + scoreFormat(scores[id+3].score, scores[id+3].battu) + "        # ");
            println(readLine(file));
        }
        for (int id = 0; id < 6; id += 1) {
            print(readLine(file));
        }
        print(scoreFormat(score, battu));
        for (int id = 0; id < 6; id += 1) {
            println(readLine(file));
        }
    }
    void affichScoreAlt(Score[] scores) { //version alternative de la fonction précédente sans la boite de texte affichant notre propre score
        extensions.File file = newFile("ressources/img/scoreMenu/HiScore.txt");
        for (int id = 0; id < 6; id += 1) {
            println(readLine(file));
        }
        for (int id = 0; id < 3; id += 1) {
            println(" #        " + (id+1) + " - " + scores[id].nom + " : " + scoreFormat(scores[id].score, scores[id].battu) + "              " + (id+4) + " - " + scores[id+3].nom + " : " + scoreFormat(scores[id+3].score, scores[id+3].battu) + "        # ");
            println(readLine(file));
        }
        for (int id = 0; id < 11; id += 1) {
            println(readLine(file));
        }
    }
    void affichEnterScore(Score[] scores) { //afficher l'écran d'entrée de pseudo
        extensions.File file = newFile("ressources/img/scoreMenu/enterScore.txt");
        for (int id = 0; id < 6; id += 1) {
            println(readLine(file));
        }
        for (int id = 0; id < 3; id += 1) {
            println(" #        " + (id+1) + " - " + scores[id].nom + " : " + scoreFormat(scores[id].score, scores[id].battu) + "              " + (id+4) + " - " + scores[id+3].nom + " : " + scoreFormat(scores[id+3].score, scores[id+3].battu) + "        # ");
            println(readLine(file));
        }
        for (int id = 0; id < 11; id += 1) {
            println(readLine(file));
        }
    }
    String[][] scoresTabToString(Score[] scores){ //donne les scores sous forme de tableau de Strings
        String[][] strScores = new String[7][3];
        strScores[0][0] = "score";
        strScores[0][1] = "nom";
        strScores[0][2] = "battu";
        for (int id = 0; id < 6; id += 1) {
            strScores[id+1][0] = "" + scores[id].score;
            strScores[id+1][1] = scores[id].nom;
            strScores[id+1][2] = boolToString(scores[id].battu);
        }
        return(strScores);
    }
    void resetBossQuestionsIfAllUsed(Question[] questions) { //permet de faire reboucler les questions du boss si elles sont toutes passées (techniquement inateignable)
        boolean test = false;
        int id = 0;
        while (id < length(questions) && !test) {
            test = questions[id].dejaPasse;
        }
        if (test) {
            for (int id2 = 0; id2 < length(questions); id2 += 1) {
                questions[id2].dejaPasse = false;
            }
        }
    }
    String boolToString(boolean bool) {
        if (bool) {
            return "true";
        }
        return "false";
    }

    //parties du jeu
    void titleScreen() {
        String[] img = initImg("titleScreen");
        int idImg = 0;
        double endTime = getTime() + 7000;
        double frameTime = getTime() + 1000;
        println(img[0]);
        while (getTime() < endTime) {
            if (getTime() > frameTime) {
                idImg = idSup(img, idImg);
                println(img[idImg]);
                frameTime = getTime() + 1000;
            }
        }
        img = initImg("titleScreenTrans");
        idImg = 0;
        frameTime = getTime() + 50;
        println(img[0]);
        while (idImg < length(img)-1) {
            delay(50);
            idImg = idSup(img, idImg);
            println(img[idImg]);
        }
    }
    void cinematique(String csvName) {
        CSVFile fileList = loadCSV("ressources/data/cinematiques/"+csvName+".csv");
        int nbImg = rowCount(fileList);
        for (int id = 1; id < nbImg; id += 1) {
            affichAnimFromCSV(fileList, id);
        }
    }
    void affichQuestion(int vies, String mat, Question ques, int score, String battleBg) {
        showQuestion(vies, ques, score);
        showRéponses(ques);
        showBattle(mat, battleBg);
    }
    void reussiQuestion(int vies, String mat, Question ques, int score, int addScore, String battleBg) {
        String[] img = initImg("question/mnstr/beat/"+battleBg);
        int idImg = 0;
        println(img[0]);
        while (idImg < length(img)-1) {
            delay(75);
            idImg = idSup(img, idImg);
            println(img[idImg]);
        }
        delay(2000);
        idImg = 0;
        double endTime = getTime() + 5000;
        affichScoreBattle(score-addScore,score,idImg,battleBg);
        while (getTime() < endTime) {
            delay(500);
            idImg = idSup(new String[2], idImg);
            affichScoreBattle(score-addScore,score,idImg,battleBg);
        }
    }
    void reussiQuestionBoss(int score, int addScore) {
        String[] img = initImg("question/mnstr/beat/bossBg");
        int idImg = 0;
        println(img[0]);
        double endTime = getTime() + 3000;
        while (getTime() < endTime) {
            delay(250);
            idImg = idSup(img, idImg);
            println(img[idImg]);
        }
        delay(1000);
        idImg = 0;
        endTime = getTime() + 5000;
        affichScoreBattle(score-addScore,score,idImg,"bossBg");
        while (getTime() < endTime) {
            delay(500);
            idImg = idSup(new String[2], idImg);
            affichScoreBattle(score-addScore,score,idImg,"bossBg");
        }
    }
    void ratéQuestion(String mat, String battleBg) {
        extensions.File main0;
        extensions.File main1;
        extensions.File mnstr;
        double endTime = getTime() + 3000;
        while (getTime() < endTime) {
            main0 = newFile("ressources/img/question/mnstr/lose/"+battleBg+"/0.txt");
            mnstr = newFile("ressources/img/question/mnstr/"+mat+".txt");
            for (int id = 0; id < 14; id += 1) {
                println(readLine(main0));
            }
            for (int id = 0; id < 7; id += 1) {
            print(readLine(main0));
            print(readLine(mnstr));
            println(readLine(main0));
            }
            println(readLine(main0));
            println(readLine(main0));
            delay(250);

            main1 = newFile("ressources/img/question/mnstr/lose/"+battleBg+"/1.txt");
            mnstr = newFile("ressources/img/question/mnstr/"+mat+".txt");
            for (int id = 0; id < 14; id += 1) {
                println(readLine(main1));
            }
            for (int id = 0; id < 7; id += 1) {
            print(readLine(main1));
            print(readLine(mnstr));
            println(readLine(main1));
            }
            println(readLine(main1));
            println(readLine(main1));
            delay(250);
        }
    }
    void mort() {
        String[] img = initImg("perdu");
        double endTime = getTime() + 5000;
        println(img[0]);
        while (getTime() < endTime) {
            //tourne pendant 5 sec
        }
    }
    void scoreScreen(int score, Score[] scores, boolean battu) {
        if (score < getLowestScore(scores)) {
            affichScore(score, scores, battu);
        } else {
            String name = "aaaaaaa";
            while (length(name) > 6) {
                affichEnterScore(scores);
                name = readString();
            }
            name = nameFormat(name);
            scores[getLowestScoreId(scores)].nom = name;
            scores[getLowestScoreId(scores)].score = score;
            scores[getLowestScoreId(scores)].battu = battu;
            saveCSV(scoresTabToString(scores), "ressources/data/score.csv");
            sortScores(scores);
            affichScoreAlt(scores);
        }
    }

    //algorithm
    void algorithm() {
        //Initialisation des valeurs de base
        Matière[] matières = loadData();
        Score[] scores = loadScores();
        int vies = 5;
        int score = 300;
        boolean battu = false;

        //Intro du jeu
        titleScreen();
        delay(3000);
        cinematique("intro");
        delay(3000);

        //Questions dans la plaine
        String battleBg = "base";
        for (int questionId = 0; questionId < 0; questionId += 1){
            if (vies != 0) {
                double startTime = getTime();
                int addScore;
                int idMat = 0;
                while (idMat == 0) {
                    idMat = (int) (random() * (double) length(matières));
                }
                String nomMat = matières[idMat].mat;
                Question ques = chargerQuestion(matières, idMat);
                affichQuestion(vies, nomMat, ques, score, battleBg);
                boolean bonneRep = entrerRep(vies, nomMat, ques, score, battleBg);
                if (bonneRep) {
                    addScore = score(startTime, getTime());
                    score += addScore;
                    reussiQuestion(vies, nomMat, ques, score, addScore, battleBg);
                } else {
                    vies -= 1;
                    ratéQuestion(nomMat, battleBg);
                }
            }
        }

        //Transition vers le chateau
        if (vies != 0) {
            cinematique("transChateau");
        }

        //Questions dans le chateau
        battleBg = "castle";
        for (int questionId = 0; questionId < 0; questionId += 1){
            if (vies != 0) {
                double startTime = getTime();
                int addScore;
                int idMat = 0;
                while (idMat == 0) {
                    idMat = (int) (random() * (double) length(matières));
                }
                String nomMat = matières[idMat].mat;
                Question ques = chargerQuestion(matières, idMat);
                affichQuestion(vies, nomMat, ques, score, battleBg);
                boolean bonneRep = entrerRep(vies, nomMat, ques, score, battleBg);
                if (bonneRep) {
                    addScore = score(startTime, getTime());
                    score += addScore;
                    reussiQuestion(vies, nomMat, ques, score, addScore, battleBg);
                } else {
                    vies -= 1;
                    ratéQuestion(nomMat, battleBg);
                }
            }
        }

        //Combat contre le boss
        if (vies != 0) {
            cinematique("bossBattleStart");

            battleBg = "bossBg";
            int bossVies = 3;
            while (vies != 0 && bossVies != 0) {
                if (vies != 0) {
                    double startTime = getTime();
                    int addScore;
                    Question ques = chargerQuestion(matières, 0);
                    affichQuestion(vies, "boss", ques, score, battleBg);
                    boolean bonneRep = entrerRep(vies, "boss", ques, score, battleBg);
                    if (bonneRep) {
                        bossVies -= 1;
                        addScore = score(startTime, getTime());
                        score += addScore;
                        reussiQuestionBoss(score, addScore);
                    } else {
                        vies -= 1;
                        ratéQuestion("boss", battleBg);
                    }
                    resetBossQuestionsIfAllUsed(matières.questions[0]);
                }
            }
        }

        if (vies == 0) {
            mort(); //Écran de mort
        } else {
            cinematique("bossBattleWon"); //Cinématique de fin de jeu
            battu = true;
        }

        scoreScreen(score, scores, battu);
    }
}