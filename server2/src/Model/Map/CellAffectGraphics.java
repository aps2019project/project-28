package Model.Map;

import Model.Graphics.Listeners.OnCellEffectedListener;

import java.util.ArrayList;

public class CellAffectGraphics {

    public static CellAffectGraphics fire=new CellAffectGraphics();
    public static CellAffectGraphics poison=new CellAffectGraphics();
    public static CellAffectGraphics holy=new CellAffectGraphics();

    private ArrayList<OnCellEffectedListener> cellEffectedListeners= new ArrayList<>();

    public  void addNewListener(OnCellEffectedListener listener){
        this.cellEffectedListeners.add(listener);
    }
    public void show(Cell cell){
        this.cellEffectedListeners.forEach(l->l.show(cell));
    }
}
