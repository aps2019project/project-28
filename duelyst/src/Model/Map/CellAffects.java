package Model.Map;


public enum CellAffects{

     fire(CellAffectGraphics.fire) , poison(CellAffectGraphics.poison) , holy(CellAffectGraphics.holy);

     private CellAffectGraphics graphics;

     CellAffects(CellAffectGraphics graphics) {
          this.graphics = graphics;
     }

     public CellAffectGraphics getGraphics(){
          return this.graphics;
     }
}
