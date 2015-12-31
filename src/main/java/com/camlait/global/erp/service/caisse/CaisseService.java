package com.camlait.global.erp.service.caisse;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyIllegalArgumentException;
import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyObjectNoFindException;

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
import com.camlait.global.erp.domain.operation.caisse.Caisse;
import com.camlait.global.erp.domain.operation.caisse.JournalCaisse;
import com.camlait.global.erp.domain.operation.caisse.OperationDeCaisse;

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
        verifyIllegalArgumentException(caisse, "caisse");
        caisseDao.save(caisse);
        return caisse;
    }
    
    @Override
    public Caisse modifierCaisse(Caisse caisse) {
        verifyIllegalArgumentException(caisse, "caisse");
        caisse.setDerniereMiseAJour(new Date());
        caisseDao.saveAndFlush(caisse);
        return caisse;
    }
    
    @Override
    public Caisse obtenirCaisse(Long caisseId) {
        verifyIllegalArgumentException(caisseId, "caisseId");
        final Caisse c = caisseDao.findOne(caisseId);
        verifyObjectNoFindException(c, Caisse.class, caisseId);
        return c;
    }
    
    @Override
    public Caisse obtenirCaisse(String codeCaisse) {
        verifyIllegalArgumentException(codeCaisse, "codeCaisse");
        final List<Caisse> caisses = caisseDao.findByCodeCaisse(codeCaisse, new PageRequest(0, 1)).getContent();
        final Caisse c = (caisses.isEmpty()) ? null : caisses.get(0);
        verifyObjectNoFindException(c, Caisse.class, codeCaisse);
        return c;
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
        verifyIllegalArgumentException(journal, "journal");
        journalCaisseDao.save(journal);
        return journal;
    }
    
    @Override
    public JournalCaisse modifierJournalCaisse(JournalCaisse journal) {
        verifyIllegalArgumentException(journal, "journal");
        journal.setDerniereMiseAJour(new Date());
        journalCaisseDao.saveAndFlush(journal);
        return journal;
    }
    
    @Override
    public JournalCaisse obtenirJournalCaisse(Long journalId) {
        verifyIllegalArgumentException(journalId, "journalId");
        final JournalCaisse j = journalCaisseDao.findOne(journalId);
        verifyObjectNoFindException(j, JournalCaisse.class, journalId);
        Hibernate.initialize(j.getOpreations());
        return j;
    }
    
    @Override
    public JournalCaisse obtenirJournalCaisse(String codeJournal) {
        verifyIllegalArgumentException(codeJournal, "codeJournal");
        final List<JournalCaisse> journaux = journalCaisseDao.findByCodeJournal(codeJournal, new PageRequest(0, 1)).getContent();
        final JournalCaisse j = (journaux.isEmpty()) ? null : journaux.get(0);
        verifyObjectNoFindException(j, JournalCaisse.class, codeJournal);
        Hibernate.initialize(j.getOpreations());
        return j;
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
        verifyIllegalArgumentException(operation, "operation");
        operationCaisseDao.save(operation);
        return operation;
    }
    
    @Override
    public OperationDeCaisse modifierOperationDeCaisse(OperationDeCaisse operation) {
        verifyIllegalArgumentException(operation, "operation");
        operation.setDerniereMiseAJour(new Date());
        operationCaisseDao.saveAndFlush(operation);
        return operation;
    }
    
    @Override
    public OperationDeCaisse obtenirOperationDeCaisse(Long operationId) {
        verifyIllegalArgumentException(operationId, "operationId");
        final OperationDeCaisse o = operationCaisseDao.findOne(operationId);
        verifyObjectNoFindException(o, OperationDeCaisse.class, operationId);
        return o;
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
