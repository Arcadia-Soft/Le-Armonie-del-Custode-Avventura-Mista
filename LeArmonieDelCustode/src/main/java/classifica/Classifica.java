package classifica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Classifica implements Serializable {

    private List<Record> records;

    /**
     * Costruttore classe Classifica
     */
    public Classifica() {
        records = new ArrayList<>();
    }

    /**
     * 
     * @param record
     */
    public void addRecord(Record record) {
        records.add(record);
    }

    /**
     * 
     * @return
     */
    public List<Record> getRecords() {
        return records;
    }

    /**
     * 
     * @param records
     */
    public void setRecords(List<Record> records) {
        this.records = records;
    }

    /**
     * 
     * @return
     */
    public String[][] getRecordsAsMatrix() {
        String[][] array = new String[records.size()][3];
        for (int i = 0; i < records.size(); i++) {
            array[i][0] = records.get(i).getNome();
            array[i][1] = records.get(i).timeFormatted();
            array[i][2] = records.get(i).getData();
        }
        return array;
    }
}
