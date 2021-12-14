package jsonTypes;

import java.util.List;

public class Clients {
    List<Client> Individual;

    public Clients(List<Client> Individual){
        this.Individual = Individual;
    }

    public List<Client> getIndividual() {
        return Individual;
    }

    public void setIndividual(List<Client> individual) {
        Individual = individual;
    }

}
