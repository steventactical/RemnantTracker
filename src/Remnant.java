public class Remnant {
    //variables for each remnant
    private String name;
    private double thickness;
    private String material;
    private double weight;
    private String heatNumber;

    public Remnant(String name, double thickness, String material, double weight, String heatNumber) {
        this.name = name;
        this.thickness = thickness;
        this.material = material;
        this.weight = weight;
        this.heatNumber = heatNumber;
    }

    public String toString() {
        return name + " - " + material + " (" + thickness + " thick, " + weight + " lbs, Heat #" + heatNumber + ")";
    }
}
