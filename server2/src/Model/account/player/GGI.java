package Model.account.player;


public class GGI implements GameInterFace {
    private Player player;

    @Override
    public void intervene() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPlayer(Player player) {
        this.player=player;
    }
}
