package barrigarest;

public class Conta {

    private long id;
    private String nome;
    private boolean visivel;
    private long usuario_id;

    public Conta() {

    }

    public Conta(long id, String nome, boolean visivel, long usuario_id) {
        this.id = id;
        this.nome = nome;
        this.visivel = visivel;
        this.usuario_id = usuario_id;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(long usuario_id) {
        this.usuario_id = usuario_id;
    }

    @Override
    public String toString() {
        return "Conta [id=" + id + ", nome=" + nome + ", usuario_id=" + usuario_id + ", visivel=" + visivel + "]";
    }
    
}
