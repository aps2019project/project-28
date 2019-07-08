package stuff;

public enum Resources {
    mouse_auto("resources/ui/mouse_auto.png") ,
    mouse_auto_2x("resources/ui/mouse_auto@2x.png") ,
    mouse_disabled("resources/ui/mouse_disabled.png") ,
    mouse_attack("resources/ui/mouse_attack.png") ,
    mouse_assist("resources/ui/mouse_assist.png") ,
    mouse_card("resources/ui/mouse_card.png"),

    backButton("resources/ui/button_back_corner.png")

    ;
    private String path ;

    Resources(String path) {
        this.path = path ;
    }

    public String getPath() {
        return path;
    }
}
