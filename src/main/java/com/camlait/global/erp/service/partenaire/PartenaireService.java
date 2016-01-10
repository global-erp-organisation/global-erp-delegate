package com.camlait.global.erp.service.partenaire;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyIllegalArgumentException;
import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyObjectNoFindException;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.immobilisation.ImmobilisationDao;
import com.camlait.global.erp.dao.immobilisation.PartenaireImmobilisationDao;
import com.camlait.global.erp.dao.partenaire.EmploisDao;
import com.camlait.global.erp.dao.partenaire.EmployeDao;
import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;
import com.camlait.global.erp.domain.immobilisation.Immobilisation;
import com.camlait.global.erp.domain.immobilisation.PartenaireImmobilisation;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.Emplois;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;

@Transactional
public class PartenaireService implements IPartenaireService {
    
    @Autowired
    private PartenaireDao partenaireDao;
    
    @Autowired
    private EmployeDao employeDao;
    
    //@Autowired
    //private IUtilService utilService;
    
    @Autowired
    private EmploisDao emploisDao;
    
    @Autowired
    private ImmobilisationDao immoDao;
    
    @Autowired
    private PartenaireImmobilisationDao pimmoDao;
    
    @Override
    public Partenaire ajouterPartenaire(Partenaire partenaire) {
        verifyIllegalArgumentException(partenaire, "partenaire");
        // partenaire.setCodePartenaire(utilService.genererCode(partenaire));
        partenaireDao.save(partenaire);
        return partenaire;
    }
    
    @Override
    public Partenaire modifierPartenaire(Partenaire partenaire) {
        verifyIllegalArgumentException(partenaire, "partenaire");
        partenaire.setDerniereMiseAJour(new Date());
        partenaireDao.saveAndFlush(partenaire);
        return partenaire;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirPartenaire(Class<T> entityClass, Long partenaireId) {
        verifyIllegalArgumentException(partenaireId, "partenaireId");
        final Partenaire p = partenaireDao.findOne(partenaireId);
        verifyObjectNoFindException(p, entityClass, partenaireId);
        Hibernate.initialize(p.getDocuments());
        if (p instanceof ClientAmarge) {
            Hibernate.initialize(((ClientAmarge) p).getMargeClients());
        }
        if (p instanceof Vendeur) {
            Hibernate.initialize(((Vendeur) p).getManquantFinanciers());
            Hibernate.initialize(((Vendeur) p).getPartenaireImmobilisations());
        }
        return (T) p;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirPartenaire(Class<T> entityClass, String codePartenaire) {
        verifyIllegalArgumentException(codePartenaire, "codePartenaire");
        final List<Partenaire> partenaires = partenaireDao.findByCodePartenaire(codePartenaire, new PageRequest(0, 1)).getContent();
        final Partenaire p = (partenaires.isEmpty()) ? null : partenaires.get(0);
        verifyObjectNoFindException(p, entityClass, codePartenaire);
        Hibernate.initialize(p.getDocuments());
        if (p instanceof ClientAmarge) {
            Hibernate.initialize(((ClientAmarge) p).getMargeClients());
        }
        if (p instanceof Vendeur) {
            Hibernate.initialize(((Vendeur) p).getManquantFinanciers());
            Hibernate.initialize(((Vendeur) p).getPartenaireImmobilisations());
        }
        return (T) p;
    }
    
    @Override
    public void supprimerPartenaire(Long partenaireId) {
        partenaireDao.delete(obtenirPartenaire(Partenaire.class, partenaireId));
    }
    
    @Override
    public Page<Partenaire> listerPartenaire(Pageable p) {
        return partenaireDao.findAll(p);
    }
    
    @Override
    public Page<Employe> listerEmploye(String motCle, Pageable p) {
        return employeDao.listerEmploye(motCle, p);
    }
    
    @Override
    public Emplois ajouterEmplois(Emplois emplois) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(emplois, "emplois");
        emploisDao.save(emplois);
        return emplois;
    }
    
    @Override
    public Emplois modifierEmplois(Emplois emplois) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(emplois, "emplois");
        emploisDao.saveAndFlush(emplois);
        return emplois;
    }
    
    @Override
    public Emplois obtenirEmplois(Long emploisId) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(emploisId, "emploisId");
        Emplois e = emploisDao.findOne(emploisId);
        verifyObjectNoFindException(e, Emplois.class, emploisId);
        Hibernate.initialize(e.getEmployes());
        return e;
    }
    
    @Override
    public Emplois obtenirEmplois(String codeEmplois) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(codeEmplois, "codeEmplois");
        List<Emplois> emplois = emploisDao.findByCodeEmplois(codeEmplois, new PageRequest(0, 1)).getContent();
        Emplois e = (emplois.isEmpty()) ? null : emplois.get(0);
        verifyObjectNoFindException(e, Emplois.class, codeEmplois);
        Hibernate.initialize(e.getEmployes());
        return e;
    }
    
    @Override
    public Page<Emplois> listerEmplois(Pageable p) throws GlobalErpServiceException, IllegalArgumentException {
        return emploisDao.findAll(p);
    }
    
    @Override
    public Immobilisation ajouterImmobilisation(Immobilisation immo) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(immo, "immo");
        immoDao.save(immo);
        return immo;
    }
    
    @Override
    public Immobilisation modifierImmobilisation(Immobilisation immo) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(immo, "immo");
        immoDao.saveAndFlush(immo);
        return immo;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirImmobilisation(Class<T> entityClass, Long immoId) throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
        verifyIllegalArgumentException(immoId, "immoId");
        Immobilisation i = immoDao.findOne(immoId);
        verifyObjectNoFindException(i, entityClass, immoId);
        return (T) i;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirImmobilisation(Class<T> entityClass, String codeImmo)
            throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
        verifyIllegalArgumentException(codeImmo, "codeImmo");
        List<Immobilisation> immos = immoDao.findByCodeImmo(codeImmo, new PageRequest(0, 1)).getContent();
        Immobilisation i = (immos.isEmpty()) ? null : immos.get(0);
        verifyObjectNoFindException(i, entityClass, codeImmo);
        return (T) i;
    }
    
    @Override
    public Page<Immobilisation> listerImmobilsation(Pageable p) throws GlobalErpServiceException, IllegalArgumentException {
        return immoDao.findAll(p);
    }
    
    @Override
    public PartenaireImmobilisation ajouterPartenaireImmobilisation(PartenaireImmobilisation pimmo)
            throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(pimmo, "partenaireImmobilisation");
        pimmoDao.save(pimmo);
        return pimmo;
    }
    
    @Override
    public PartenaireImmobilisation modifierPartenaireImmobilisation(PartenaireImmobilisation pimmo)
            throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(pimmo, "partenaireImmobilisation");
        pimmoDao.saveAndFlush(pimmo);
        return pimmo;
    }
    
    @Override
    public PartenaireImmobilisation obtenirPartenaireImmobilisation(Long pimmoId) throws GlobalErpServiceException, IllegalArgumentException {
        verifyIllegalArgumentException(pimmoId, "pimmoId");
        PartenaireImmobilisation p = pimmoDao.findOne(pimmoId);
        verifyObjectNoFindException(p, PartenaireImmobilisation.class, pimmoId);
        return p;
    }
    
    @Override
    public void supprimerPartenaireImmobilisation(Long pimmoId) throws GlobalErpServiceException, IllegalArgumentException {
        pimmoDao.delete(obtenirPartenaireImmobilisation(pimmoId));
    }
    
    @Override
    public void supprimerPartenaire(String codePartenaire) throws GlobalErpServiceException, IllegalArgumentException, ClassCastException {
        partenaireDao.delete(obtenirPartenaire(Partenaire.class, codePartenaire));
    }
    
    @Override
    public void supprimerEmplois(Long emploisId) throws GlobalErpServiceException, IllegalArgumentException {
        emploisDao.delete(obtenirEmplois(emploisId));
    }
    
    @Override
    public void supprimerEmplois(String codeEmplois) throws GlobalErpServiceException, IllegalArgumentException {
        emploisDao.delete(obtenirEmplois(codeEmplois));
    }
    
    @Override
    public void supprimerImmobilisation(Long immoId) throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
        immoDao.delete(obtenirImmobilisation(Immobilisation.class, immoId));
    }
    
    @Override
    public void supprimerImmobilisation(String codeImmo) throws GlobalErpServiceException, ClassCastException, IllegalArgumentException {
        immoDao.delete(obtenirImmobilisation(Immobilisation.class, codeImmo));
    }   
}
