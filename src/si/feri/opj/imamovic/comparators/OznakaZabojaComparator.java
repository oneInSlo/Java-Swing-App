package si.feri.opj.imamovic.comparators;

import si.feri.opj.imamovic.tretji.OznakaZaboja;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class OznakaZabojaComparator implements Comparator<OznakaZaboja> {

    @Override
    public int compare(OznakaZaboja o1, OznakaZaboja o2) {
        Collator collator = Collator.getInstance(new Locale("sl", "SI"));
        return collator.compare(o1.name(), o2.name());
    }

}
