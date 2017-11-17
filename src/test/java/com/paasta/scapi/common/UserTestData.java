package com.paasta.scapi.common;
import sonia.scm.user.User;

public final class UserTestData
{

    private UserTestData() {}




    public static User createDent()
    {
        return new User("dent", "Arthur Dent", "arthur.dent@hitchhiker.com");
    }


    public static User createMarvin()
    {
        return new User("marvin", "Marvin", "paranoid.android@hitchhiker.com");
    }


    public static User createPerfect()
    {
        return new User("perfect", "Ford Prefect", "ford.perfect@hitchhiker.com");
    }

    public static User createSlarti()
    {
        return new User("slarti", "Slartibartfaß", "slartibartfass@hitchhiker.com");
    }

    public static User createTrillian()
    {
        return new User("trillian", "Tricia McMillan",
                "tricia.mcmillan@hitchhiker.com");
    }

    public static User createZaphod()
    {
        return new User("zaphod", "Zaphod Beeblebrox",
                "zaphod.beeblebrox@hitchhiker.com");
    }
}