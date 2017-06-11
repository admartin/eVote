package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class PoliticalPartyImpl extends Persistent implements PoliticalParty {
        
        private String name;
        private List<Candidate> Candidates;
        
        public PoliticalPartyImpl() {
          
           name = null;
           Candidates = null;
        }
        
        public PoliticalPartyImpl(long id, String name, List<Candidate> Candidates) {
          
            this.name = name;
            this.Candidates = Candidates;
        }
	@Override
	public long getId() {
		return super.getId();
	}

	@Override
	public void setId(long id) {
		super.setId(id);

	}
	

	@Override
	public boolean isPersistent() {
		return super.isPersistent();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public List<Candidate> getCandidates() throws EVException {
		if(Candidates == null )
            if( isPersistent() ) {
                Candidate candidate = new CandidateImpl();
                candidate.setPoliticalParty(this);
                Candidates = getPersistencaLayer().restoreCandidate(candidate);
            }
            else
                throw new EVException( "Not Persistant" );
        
        return Candidates;
    }

	@Override
        public String toString()
        {
        return "PoliticalParty[" + getName() + "] " ;
        }

}