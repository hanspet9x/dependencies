package tbs.models;

import java.util.List;

public class Tariffs {

   List<BoreholeLicenseTarrifs> boreholeLicenseTarrifs;
   List<SewerageTarrifs> sewerageTarrifs;
   List<MeteredWaterTarrifs> meteredWaterTarrifs;
   List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs;

    public Tariffs(List<BoreholeLicenseTarrifs> boreholeLicenseTarrifs, List<SewerageTarrifs> sewerageTarrifs, List<MeteredWaterTarrifs> meteredWaterTarrifs, List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs) {
        this.boreholeLicenseTarrifs = boreholeLicenseTarrifs;
        this.sewerageTarrifs = sewerageTarrifs;
        this.meteredWaterTarrifs = meteredWaterTarrifs;
        this.unmeteredWaterTarrifs = unmeteredWaterTarrifs;
    }

    public List<BoreholeLicenseTarrifs> getBoreholeLicenseTarrifs() {
        return boreholeLicenseTarrifs;
    }

    public void setBoreholeLicenseTarrifs(List<BoreholeLicenseTarrifs> boreholeLicenseTarrifs) {
        this.boreholeLicenseTarrifs = boreholeLicenseTarrifs;
    }

    public List<SewerageTarrifs> getSewerageTarrifs() {
        return sewerageTarrifs;
    }

    public void setSewerageTarrifs(List<SewerageTarrifs> sewerageTarrifs) {
        this.sewerageTarrifs = sewerageTarrifs;
    }

    public List<MeteredWaterTarrifs> getMeteredWaterTarrifs() {
        return meteredWaterTarrifs;
    }

    public void setMeteredWaterTarrifs(List<MeteredWaterTarrifs> meteredWaterTarrifs) {
        this.meteredWaterTarrifs = meteredWaterTarrifs;
    }

    public List<UnmeteredWaterTarrifs> getUnmeteredWaterTarrifs() {
        return unmeteredWaterTarrifs;
    }

    public void setUnmeteredWaterTarrifs(List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs) {
        this.unmeteredWaterTarrifs = unmeteredWaterTarrifs;
    }
}
