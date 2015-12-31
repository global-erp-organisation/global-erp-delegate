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

import com.camlait.global.erp.dao.partenaire.EmployeDao;
import com.camlait.global.erp.dao.partenaire.PartenaireDao;
import com.camlait.global.erp.domain.partenaire.ClientAmarge;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Partenaire;
import com.camlait.global.erp.domain.partenaire.Vendeur;
import com.camlait.global.erp.service.util.IUtilService;

@Transactional
public class PartenaireService implements IPartenaireService {
    
    @Autowired
    private PartenaireDao partenaireDao;
    
    @Autowired
    private EmployeDao employeDao;
    
    @Autowired
    private IUtilService utilService;
    
    @Override
    public Partenaire ajouterPartenaire(Partenaire partenaire) {
        verifyIllegalArgumentException(partenaire, "partenaire");
        partenaire.setCodePartenaire(utilService.genererCode(partenaire));
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
    public <T> T obtanirPartenaire(Class<T> entityClass, Long partenaireId) {
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
    public <T> T obtanirPartenaire(Class<T> entityClass, String codePartenaire) {
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
        partenaireDao.delete(obtanirPartenaire(Partenaire.class, partenaireId));
    }
    
    @Override
    public Page<Partenaire> listerPartenaire(Pageable p) {
        return partenaireDao.findAll(p);
    }
    
    @Override
    public Page<Employe> listerEmploye(String motCle, Pageable p) {
        return employeDao.listerEmploye(motCle, p);
    }
    
}
