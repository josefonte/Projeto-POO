import java.io.*;
import java.util.*;

public class Estado implements Serializable {

    private HashMap<String,CasaInteligente> housesConfig;
    private HashMap<String,Fornecedor> fornecedores;

    public Estado(){
        this.housesConfig = new HashMap<>();
        this.fornecedores = new HashMap<>();
    }

    public Estado(HashMap<String,CasaInteligente> housesConfig, HashMap<String,Fornecedor> fornecedores){
        this.housesConfig = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : housesConfig.entrySet()){
            this.housesConfig.put(ent.getKey(),ent.getValue().clone());
        }

        this.fornecedores = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : fornecedores.entrySet()){
            this.fornecedores.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public Estado(Estado est){
        this.housesConfig = est.getHousesConfig();
        this.fornecedores = est.getFornecedores();
    }


    public HashMap<String, CasaInteligente> getHousesConfig() {
        HashMap<String,CasaInteligente> casas = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : this.housesConfig.entrySet()){
            casas.put(ent.getKey(),ent.getValue().clone());
        }
        return casas;
    }

    public HashMap<String, Fornecedor> getFornecedores() {
        HashMap<String,Fornecedor> forns = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : this.fornecedores.entrySet()){
            forns.put(ent.getKey(),ent.getValue().clone());
        }
        return forns;
    }

    public void setFornecedores(HashMap<String, Fornecedor> fornecedores) {
        this.fornecedores = new HashMap<>();
        for(Map.Entry<String,Fornecedor> ent : fornecedores.entrySet()){
            this.fornecedores.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public void setHousesConfig(HashMap<String, CasaInteligente> housesConfig) {
        this.housesConfig = new HashMap<>();
        for(Map.Entry<String,CasaInteligente> ent : housesConfig.entrySet()){
            this.housesConfig.put(ent.getKey(),ent.getValue().clone());
        }
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if( obj == null || obj.getClass()!=this.getClass()) return false;
        Estado stt = (Estado) obj;
        return this.housesConfig.equals(stt.getHousesConfig()) && this.fornecedores.equals(stt.getFornecedores());
     }

    public String toString(){
        return this.housesConfig.entrySet() + this.housesConfig.entrySet().toString();
    }

    public Estado clone(){
        return new Estado(this);
    }

    //---------------------------------------------------------------------


    public void adicionaCasa(CasaInteligente casa){
        this.housesConfig.put(casa.getOwner(),casa);
    }
    public void removeCasa(CasaInteligente casa){
        this.housesConfig.remove(casa.getOwner());
    }
    public void adicionaFornecedor(Fornecedor fornecedor){
        if(!fornecedores.containsKey(fornecedor.getName()))
            this.fornecedores.put(fornecedor.getName(),fornecedor);
    }

    public void removeFornecedor(Fornecedor fornecedor){
        this.housesConfig.remove(fornecedor.getName());
    }

    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        fos.close();
    }

    public Estado carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fos = new FileInputStream(nomeFicheiro);
        ObjectInputStream oos = new ObjectInputStream(fos);
        Estado c = (Estado) oos.readObject();
        oos.close();
        fos.close();
        return c;
    }

    /*Estatisticas
    - casa que gastou mais
    - fornecedor com maior volume de faturação
    - todas as faturas por fornecedor
    - casas com maior consumo durante X tempo
*/
    public CasaInteligente casaMaisGastou(){
        TreeSet<CasaInteligente> set_casas = new TreeSet<>();

        this.housesConfig.values().stream()
                        .forEach(a->set_casas.add(a.clone()));

        return set_casas.first();
    }


}
