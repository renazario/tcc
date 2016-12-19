package br.com.marisamu.controle;

import br.com.marisamu.converter.GenericConverter;
import br.com.marisamu.exception.PersistenceException;
import br.com.marisamu.facade.AbstractFacade;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author renan
 */
public abstract class AbstractControle<T> {

    private Class<T> classe;
    private T entidade;
    private List<T> listaEntidades;
    private GenericConverter<T> converter;

    public AbstractControle(Class<T> classe) {
        this.classe = classe;
    }

    public GenericConverter getConverter() {
        if (converter == null) {
            converter = new GenericConverter<>(getFacade());
        }
        return converter;
    }

    public String novo() {
        try {
            entidade = classe.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return "form?faces-redirect=true";
    }

    protected abstract AbstractFacade<T> getFacade();

    private void mensagem(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String salvar() {
        getFacade().salvar(entidade);
        entidade = null;
        listaEntidades = null;
//        mensagem("Salvo com sucesso!",
//                FacesMessage.SEVERITY_INFO);
        return "list?faces-redirect=true";
     
    }

    public void excluir(){
        if (entidade != null) {
            try {
                getFacade().excluir(entidade);
                mensagem("Excluido com sucesso!",
                        FacesMessage.SEVERITY_INFO);
            } catch (PersistenceException ex) {
                mensagem(ex.getLocalizedMessage(),
                        FacesMessage.SEVERITY_FATAL);
            }
            entidade = null;
            listaEntidades = null;
        } else {
            mensagem("Nenhum elemento foi selecionado para a exclusão!",
                    FacesMessage.SEVERITY_WARN);

        }
    }

    public String voltar() {
        entidade = null;
        listaEntidades = null;
        return "list?faces-redirect=true";
    }

    public String carregarCadastro() {
        if (entidade != null) {
            return "form?faces-redirect=true";
        } else {
            mensagem("Nenhum elemento foi selecionado para a alteração!",
                    FacesMessage.SEVERITY_WARN);
            return "list";
        }
    }

    public List<T> getListaEntidades() {
//        if (listaEntidades == null) {
            listaEntidades = getFacade().listar();
//        }
        return listaEntidades;
    }

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }
}
