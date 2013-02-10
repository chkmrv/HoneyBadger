package core;

import java.io.Serializable;

public class TestNetwork implements Serializable {
    public TestNetwork(String name) {
        this.setName(name);
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
