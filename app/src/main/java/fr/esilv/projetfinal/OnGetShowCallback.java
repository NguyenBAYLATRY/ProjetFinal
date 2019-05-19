package fr.esilv.projetfinal;
import java.util.List;

public interface OnGetShowCallback {

    void onSuccess(int page, List<TVshow> movies);

    void onError();
}
