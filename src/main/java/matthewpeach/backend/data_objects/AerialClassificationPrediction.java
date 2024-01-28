package matthewpeach.backend.data_objects;

//Class Codes
//        'BareLand': 0,
//        'Beach': 1,
//        'DenseResidential': 2,
//        'Desert': 3,
//        'Forest': 4,
//        'Mountain': 5,
//        'Parking': 6,
//        'SparseResidential': 7

public class AerialClassificationPrediction {
    private double BareLand;
    private double Beach;
    private double DenseResidential;
    private double Desert;
    private double Forest;
    private double Mountain;
    private double Parking;
    private double SparseResidential;

    public AerialClassificationPrediction(){
        // Empty constructor for Jackson
    }

    public AerialClassificationPrediction(double bareLand, double beach, double denseResidential, double desert, double forest, double mountain, double parking, double sparseResidential) {
        BareLand = bareLand;
        Beach = beach;
        DenseResidential = denseResidential;
        Desert = desert;
        Forest = forest;
        Mountain = mountain;
        Parking = parking;
        SparseResidential = sparseResidential;
    }

    public double getSparseResidential() {
        return SparseResidential;
    }

    public void setSparseResidential(double sparseResidential) {
        SparseResidential = sparseResidential;
    }

    public double getDesert() {
        return Desert;
    }

    public void setDesert(double desert) {
        Desert = desert;
    }

    public double getBareLand() {
        return BareLand;
    }

    public void setBareLand(double bareland) {
        BareLand = bareland;
    }

    public double getDenseResidential() {
        return DenseResidential;
    }

    public void setDenseResidential(double denseResidential) {
        DenseResidential = denseResidential;
    }

    public double getBeach() {
        return Beach;
    }

    public void setBeach(double beach) {
        Beach = beach;
    }

    public double getParking() {
        return Parking;
    }

    public void setParking(double parking) {
        Parking = parking;
    }

    public double getMountain() {
        return Mountain;
    }

    public void setMountain(double mountain) {
        Mountain = mountain;
    }

    public double getForest() {
        return Forest;
    }

    public void setForest(double forest) {
        Forest = forest;
    }
}
