package com.camlait.global.erp.service.organisation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.camlait.global.erp.dao.organisation.CentreDao;
import com.camlait.global.erp.dao.organisation.LocalisationDao;
import com.camlait.global.erp.dao.organisation.RegionDao;
import com.camlait.global.erp.dao.organisation.SecteurDao;
import com.camlait.global.erp.dao.organisation.ZoneDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.organisation.Centre;
import com.camlait.global.erp.domain.organisation.Localisation;
import com.camlait.global.erp.domain.organisation.Region;
import com.camlait.global.erp.domain.organisation.Secteur;
import com.camlait.global.erp.domain.organisation.Zone;
import com.camlait.global.erp.service.GlobalErpServiceException;
import com.camlait.global.erp.service.util.IUtilService;

public class LocalisationService implements ILocalisationService {
    
    @Autowired
    private LocalisationDao localisationDao;
    
    @Autowired
    private CentreDao centreDao;
    
    @Autowired
    private RegionDao regionDao;
    
    @Autowired
    private SecteurDao secteurDao;
    
    @Autowired
    private ZoneDao zoneDao;
    
    @Autowired
    private IUtilService utilService;
    
    @Override
    public Localisation ajouterLocalisation(Localisation local) {
        if (local == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("local"));
        }
        local.setCode(utilService.genererCode(local));
        localisationDao.save(local);
        return local;
    }
    
    @Override
    public Localisation modifierLocalisation(Localisation local) {
        if (local == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("local"));
        }
        local.setDerniereMiseAJour(new Date());
        localisationDao.saveAndFlush(local);
        return local;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirLocalisation(Class<T> entityClass, Long localId) {
        if (localId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("localId"));
        }
        final Localisation local = localisationDao.findOne(localId);
        if (local != null) {
            if (local instanceof Centre) {
                Hibernate.initialize((((Centre) local).getRegions()));
            } else if (local instanceof Region) {
                Hibernate.initialize((((Region) local).getSecteurs()));
            } else if (local instanceof Secteur) {
                Hibernate.initialize((((Secteur) local).getZones()));
            }
            return (T) local;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(entityClass, localId));
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirLocalisation(Class<T> entityClass, String codeLocalisation) {
        if (codeLocalisation == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeLocalisation"));
        }
        final List<Localisation> locals = localisationDao.findByCode(codeLocalisation, new PageRequest(0, 1)).getContent();
        final Localisation local = (locals.isEmpty()) ? null : locals.get(0);
        if (local != null) {
            if (local instanceof Centre) {
                Hibernate.initialize((((Centre) local).getRegions()));
            } else if (local instanceof Region) {
                Hibernate.initialize((((Region) local).getSecteurs()));
            } else if (local instanceof Secteur) {
                Hibernate.initialize((((Secteur) local).getZones()));
            }
            return (T) local;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(entityClass, codeLocalisation));
        }
    }
    
    @Override
    public Collection<Centre> listerCentre() {
        return centreDao.findAll();
    }
    
    @Override
    public Collection<Region> listerRegion(Centre centre) {
        return regionDao.listerRegion(centre.getLocalId());
    }
    
    @Override
    public Collection<Secteur> listerSecteur(Region region) {
        return secteurDao.listerSecteur(region.getLocalId());
    }
    
    @Override
    public Collection<Zone> listerZone(Secteur secteur) {
        return zoneDao.listerZone(secteur.getLocalId());
    }
    
    @Override
    public void supprimerLocalisation(Long localId) {
        localisationDao.delete(obtenirLocalisation(Localisation.class, localId));
    }
    
}
