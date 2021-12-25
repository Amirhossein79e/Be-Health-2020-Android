package com.amirhosseinemadi.behealth.common;

public class Application extends android.app.Application {

    public static DComponent dComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        dComponent = DaggerDComponent.builder().daggerModule(new DaggerModule(getApplicationContext())).build();
    }

}
