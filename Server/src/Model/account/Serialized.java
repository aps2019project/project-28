package Model.account;

import javafx.util.Pair;

import java.util.ArrayList;

public class Serialized {
    private ArrayList<Pair<String,Object>>obj=new ArrayList<>();

    public ArrayList<Pair<String, Object>> getObj() {
        return obj;
    }

    public void setObj(ArrayList<Pair<String, Object>> obj) {
        this.obj = obj;
    }
    public void add(Pair<String,Object>value){
        this.obj.add(value);
    }
}
