package in.dhaivathlal.dsshmanager.models;

public class Group {

    private Integer id;
    private String name;

    private Integer machines;


    public Group(Integer id, String name, Integer machines) {
        this.id = id;
        this.name = name;
        this.machines = machines;
    }

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.machines = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMachines() {
        return machines;
    }

    public void setMachines(Integer machines) {
        this.machines = machines;
    }
}
