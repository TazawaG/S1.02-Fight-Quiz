import extensions.CSVFile;

class resetScores extends Program {
    String[][] CSVToString(CSVFile csv) {
        String[][] str = new String[7][3];
        str[0][0] = "score";
        str[0][1] = "nom";
        str[0][2] = "battu";
        for (int id = 0; id < 6; id += 1) {
            str[id+1][0] = getCell(csv,id+1,0);
            str[id+1][1] = getCell(csv,id+1,1);
            str[id+1][2] = getCell(csv,id+1,2);
        }
        return str;
    }
    void algorithm() {
        saveCSV(CSVToString(loadCSV("ressources/data/scoreBlank.csv")), "ressources/data/score.csv");
    }
}