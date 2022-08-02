package barrigarest;

public class Movimentacao {

    private Long id;
    private String descricao;
    private String envolvido;
    private String observacao;
    private String tipo;
    private String data_transacao;
    private String data_pagamento;
    private Float valor;
    private Boolean status;
    private Long conta_id;
    private Long usuario_id;
    private Long transferencia_id;
    private Long parcelamento_id;

    public Movimentacao() {

    }

    public Movimentacao(
            String descricao, String envolvido, String tipo, 
            String data_transacao, String data_pagamento, 
            Float valor, Boolean status, Long conta_id
            ) {
        this.descricao = descricao;
        this.envolvido = envolvido;
        this.tipo = tipo;
        this.data_transacao = data_transacao;
        this.data_pagamento = data_pagamento;
        this.valor = valor;
        this.status = status;
        this.conta_id = conta_id;
    }

    public Movimentacao(
            Long id, String descricao, String envolvido, String observacao, String tipo, 
            String data_transacao, String data_pagamento, 
            Float valor, 
            Boolean status, 
            Long conta_id, 
            Long usuario_id, 
            Long transferencia_id, Long parcelamento_id) {
        this.id = id;
        this.descricao = descricao;
        this.envolvido = envolvido;
        this.observacao = observacao;
        this.tipo = tipo;
        this.data_transacao = data_transacao;
        this.data_pagamento = data_pagamento;
        this.valor = valor;
        this.status = status;
        this.conta_id = conta_id;
        this.usuario_id = usuario_id;
        this.transferencia_id = transferencia_id;
        this.parcelamento_id = parcelamento_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEnvolvido() {
        return envolvido;
    }

    public void setEnvolvido(String envolvido) {
        this.envolvido = envolvido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData_transacao() {
        return data_transacao;
    }

    public void setData_transacao(String data_transacao) {
        this.data_transacao = data_transacao;
    }

    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getConta_id() {
        return conta_id;
    }

    public void setConta_id(Long conta_id) {
        this.conta_id = conta_id;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Long getTransferencia_id() {
        return transferencia_id;
    }

    public void setTransferencia_id(Long transferencia_id) {
        this.transferencia_id = transferencia_id;
    }

    public Long getParcelamento_id() {
        return parcelamento_id;
    }

    public void setParcelamento_id(Long parcelamento_id) {
        this.parcelamento_id = parcelamento_id;
    }

    @Override
    public String toString() {
        return "Movimentacao [conta_id=" + conta_id + ", data_pagamento=" + data_pagamento + ", data_transacao="
                + data_transacao + ", descricao=" + descricao + ", envolvido=" + envolvido + ", id=" + id
                + ", observacao=" + observacao + ", parcelamento_id=" + parcelamento_id + ", status=" + status
                + ", tipo=" + tipo + ", transferencia_id=" + transferencia_id + ", usuario_id=" + usuario_id
                + ", valor=" + valor + "]";
    }

    public String toStringCamposObrigatorios() {
        return "Movimentacao [conta_id=" + conta_id + ", data_pagamento=" + data_pagamento + ", data_transacao="
                + data_transacao + ", descricao=" + descricao + ", envolvido=" + envolvido + ", id=" + id
                + ", usuario_id=" + usuario_id + ", valor=" + valor + "]";
    }
    
}
