/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.converter;

import br.com.marisamu.facade.AbstractFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author renan
 */
public class GenericConverter<T> implements Converter {

    private AbstractFacade<T> facade;

    public GenericConverter(AbstractFacade<T> facade) {
        this.facade = facade;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.equals("null")) {
            return facade.pesquisar(Long.valueOf(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
}
