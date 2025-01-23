package base;

import driver.DriverProvider;

public abstract class PageBase extends BasePage {

    public PageBase(DriverProvider driverProvider) {
        super(driverProvider);
        check();
    }

    protected abstract void check();
}
