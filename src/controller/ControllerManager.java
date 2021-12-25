package controller;

public class ControllerManager {
    private NewViewController newViewController;
    private ViewController viewController;
    private MenuController menuController;
    private PersonalInfoController personalInfoController;
    private StatusController statusController;
    // private HelpController helpController;

    // public void addHelpController(HelpController helpController) {
    // this.helpController = helpController;
    // }

    public void addIntroController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void addViewController(NewViewController viewController) {
        this.newViewController = viewController;
    }

    // public void addEditController(EditController editController) {
    // this.editController = editController;
    // }

    // public HelpController getHelpController() {
    // return helpController;
    // }

    public MenuController getMenuController() {
        return menuController;
    }

    public NewViewController getNewViewController() {
        return newViewController;
    }

    public ViewController getViewController() {
        return viewController;
    }

    public PersonalInfoController getPersonalInfoController() {
        return personalInfoController;
    }

    public StatusController getStatusController() {
        return statusController;
    }

    public void addStatusController(StatusController statusController) {
        this.statusController = statusController;
    }

    public void addPersonalInfoController(PersonalInfoController personalInfoController) {
        this.personalInfoController = personalInfoController;
    }

    public void addViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
