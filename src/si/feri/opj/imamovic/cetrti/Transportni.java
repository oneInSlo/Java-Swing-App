package si.feri.opj.imamovic.cetrti;

import si.feri.opj.imamovic.exceptions.PredragoException;
import si.feri.opj.imamovic.prvi.*;

public interface Transportni {
    public boolean lahkoNatovori(Dimenzije dimenzijeVozila) throws PredragoException;
}
