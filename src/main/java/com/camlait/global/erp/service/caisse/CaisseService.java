package com.camlait.global.erp.service.caisse;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.operation.caisse.CaisseDao;
import com.camlait.global.erp.dao.operation.caisse.JournalCaisseDao;
import com.camlait.global.erp.dao.operation.caisse.OperationDeCaisseDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.operation.caisse.Caisse;
import com.camlait.global.erp.domain.operation.caisse.JournalCaisse;
import com.camlait.global.erp.domain.operation.caisse.OperationDeCaisse;
import com.camlait.global.erp.service.GlobalErpServiceException;

@Transactional
public class CaisseService implements ICaisseService {
    
    @Autowired
    private CaisseDao caisseDao;
    
    @Autowired
    private JournalCaisseDao journalCaisseDao;
    
    @Autowired
    private OperationDeCaisseDao operationCaisseDao;
    
    @Override
    public Caisse ajouterCaisse(Caisse caisse) {
        if (caisse == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("caisse"));
        }
        caisseDao.save(caisse);
        return caisse;
    }
    
    @Override
    public Caisse modifierCaisse(Caisse caisse) {
        if (caisse == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("caisse"));
        }
        caisse.setDerniereMiseAJour(new Date());
        caisseDao.saveAndFlush(caisse);
        return caisse;
    }
    
    @Override
    public Caisse obtenirCaisse(Long caisseId) {
        if (caisseId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("caisseId"));
        }
        final Caisse c = caisseDao.findOne(caisseId);
        if (c != null) {
            return c;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Caisse.class, caisseId));
        }
    }
    
    @Override
    public Caisse obtenirCaisse(String codeCaisse) {
        if (codeCaisse == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeCaisse"));
        }
        final List<Caisse> caisses = caisseDao.findByCodeCaisse(codeCaisse, new PageRequest(0, 1)).getContent();
        final Caisse c = (caisses.isEmpty()) ? null : caisses.get(0);
        if (c != null) {
            return c;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Caisse.class, codeCaisse));
        }
    }
    
    @Override
    public void supprimerCaisse(Long caisseId) {
        caisseDao.delete(obtenirCaisse(caisseId));
    }
    
    @Override
    public Page<Caisse> listerCaisse(Pageable p) {
        return caisseDao.findAll(p);
    }
    
    @Override
    public JournalCaisse ajouterJournalCaisse(JournalCaisse journal) {
        if (journal == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("journal"));
        }
        journalCaisseDao.save(journal);
        return journal;
    }
    
    @Override
    public JournalCaisse modifierJournalCaisse(JournalCaisse journal) {
        if (journal == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("journal"));
        }
        journal.setDerniereMiseAJour(new Date());
        journalCaisseDao.saveAndFlush(journal);
        return journal;
    }
    
    @Override
    public JournalCaisse obtenirJournalCaisse(Long journalId) {
        if (journalId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("journalId"));
        }
        final JournalCaisse j = journalCaisseDao.findOne(journalId);
        if (j != null) {
            Hibernate.initialize(j.getOpreations());
            return j;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(JournalCaisse.class, journalId));
        }
    }
    
    @Override
    public JournalCaisse obtenirJournalCaisse(String codeJournal) {
        if (codeJournal == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("codeJournal"));
        }
        final List<JournalCaisse> journaux = journalCaisseDao.findByCodeJournal(codeJournal, new PageRequest(0, 1)).getContent();
        final JournalCaisse j = (journaux.isEmpty()) ? null : journaux.get(0);
        if (j != null) {
            Hibernate.initialize(j.getOpreations());
            return j;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(JournalCaisse.class, codeJournal));
        }
    }
    
    @Override
    public void supprimerJournalCaisse(Long journalId) {
        journalCaisseDao.delete(obtenirJournalCaisse(journalId));
    }
    
    @Override
    public Page<JournalCaisse> listerJournalCaisse(Pageable p) {
        return journalCaisseDao.findAll(p);
    }
    
    @Override
    public OperationDeCaisse ajouterOperationDeCaisse(OperationDeCaisse operation) {
        if (operation == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("operation"));
        }
        operationCaisseDao.save(operation);
        return operation;
    }
    
    @Override
    public OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation) {
        if (operation == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("operation"));
        }
        operation.setDerniereMiseAJour(new Date());
        operationCaisseDao.saveAndFlush(operation);
        return operation;
    }
    
    @Override
    public OperationDeCaisse obtenirOperationDeCaisse(Long operationId) {
        if (operationId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("operationId"));
        }
        final OperationDeCaisse o = operationCaisseDao.findOne(operationId);
        if (o != null) {
            return o;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(OperationDeCaisse.class, operationId));
        }
    }
    
    @Override
    public void supprimerOperationDeCaisse(Long operationId) {
        operationCaisseDao.delete(obtenirOperationDeCaisse(operationId));
    }
    
    @Override
    public Collection<OperationDeCaisse> listerOperationDeCaisse(Long journalId) {
        return operationCaisseDao.listerOperationDeCaisse(journalId);
    }
    
    @Override
    public Collection<OperationDeCaisse> listerOperationDeCaisse(Collection<JournalCaisse> journaux) {
        final Collection<OperationDeCaisse> op = new HashSet<>();
        journaux.stream().forEach(j -> {
            op.addAll(listerOperationDeCaisse(j.getJournalId()));
        });
        return op;
    }
    
    @Override
    public void supprimerOperationDeCaisse(JournalCaisse journal) {
        operationCaisseDao.delete(journal.getOpreations());
    }
}
