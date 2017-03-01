package com.camlait.global.erp.delegate.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.camlait.global.erp.dao.bmq.BmqDao;
import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.entrepot.EntrepotDao;
import com.camlait.global.erp.dao.entrepot.MagasinDao;
import com.camlait.global.erp.dao.inventaire.InventaireDao;
import com.camlait.global.erp.dao.localisation.LocalisationDao;
import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.dm.DailyMovement;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.enumeration.EnumTypeEntite;
import com.camlait.global.erp.domain.inventory.Inventory;
import com.camlait.global.erp.domain.localisation.Localisation;
import com.camlait.global.erp.domain.partner.Partner;
import com.camlait.global.erp.domain.util.Utility;
import com.camlait.global.erp.domain.warehouse.Store;
import com.camlait.global.erp.domain.warehouse.Warehouse;

import lombok.NonNull;

public class UtilService implements IUtilService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private InventaireDao inventaireDao;

    @Autowired
    private PartenaireDao partenaireDao;

    @Autowired
    private BmqDao bmqDao;

    @Autowired
    private LocalisationDao localisationDao;

    @Autowired
    private MagasinDao magasinDao;

    @Autowired
    private EntrepotDao entrepotDao;

    @Override
    public String genererCode(Entite entite) {
        return null;
        // return dernierId(entite);
    }

    /**
     * Chercher le dernier code et generer le nouveau a partir de son numero.
     * 
     * @param entite
     *            Entite associe au code.
     * @return le nouveau code.
     */
    /*
     * private String dernierId(@NonNull Entite entite) {
     * EnumTypeEntite type = Utility.obtenirPrefixe(entite);
     * String dernierCode = null;
     * if (entite instanceof Document) {
     * List<Document> documents = documentDao.obtenirDernierDocument(type, new PageRequest(0, 1)).getContent();
     * dernierCode = (documents.isEmpty()) ? null : documents.get(0).getCodeDocument();
     * } else if (entite instanceof Partner) {
     * List<Partner> partenaires = partenaireDao.obtenirDernierPartenaire(type, new PageRequest(0, 1))
     * .getContent();
     * dernierCode = (partenaires.isEmpty()) ? null : partenaires.get(0).getCodePartenaire();
     * } else if (entite instanceof Inventory) {
     * List<Inventory> inventaires = inventaireDao.obtenirDernierInventaire(new PageRequest(0, 1)).getContent();
     * dernierCode = (inventaires.isEmpty()) ? null : inventaires.get(0).getCodeInventaire();
     * } else if (entite instanceof DailyMovement) {
     * List<DailyMovement> bmqs = bmqDao.obtenirDernierBmq(new PageRequest(0, 1)).getContent();
     * dernierCode = (bmqs.isEmpty()) ? null : bmqs.get(0).getCodeBmq();
     * } else if (entite instanceof Localisation) {
     * List<Localisation> localisations = localisationDao.obtenirDernierLocal(type, new PageRequest(0, 1))
     * .getContent();
     * dernierCode = (localisations.isEmpty()) ? null : localisations.get(0).getCode();
     * } else if (entite instanceof Store) {
     * List<Store> magasins = magasinDao.obtenirDernierMagasin(type, new PageRequest(0, 1)).getContent();
     * dernierCode = (magasins.isEmpty()) ? null : magasins.get(0).getCodeMagasin();
     * } else if (entite instanceof Warehouse) {
     * List<Warehouse> entrepots = entrepotDao.obtenirDernierEntrepot(new PageRequest(0, 1)).getContent();
     * dernierCode = (entrepots.isEmpty()) ? null : entrepots.get(0).getCodeEntrepot();
     * } else
     * throw new IllegalArgumentException("L'entité " + entite.getClass().getName() + " n'existe pas");
     * if (dernierCode == null) {
     * return type.getType().toUpperCase() + 1;
     * }
     * return type.getType().toUpperCase()
     * + (Utility.convertToLong(StringUtils.removeStart(dernierCode, type.getType())) + 1);
     * }
     */

}
