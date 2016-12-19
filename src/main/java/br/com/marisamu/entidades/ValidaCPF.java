/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

/**
 *
 * @author renan
 */
public class ValidaCPF {

    public static boolean validacpf(String strCpf) {
        if (!strCpf.equals("")) {
            if (!strCpf.replace(".","").replace("-","").equals("00000000000")
                    && !strCpf.replace(".","").replace("-","").equals("11111111111")
                    && !strCpf.replace(".","").replace("-","").equals("22222222222")
                    && !strCpf.replace(".","").replace("-","").equals("33333333333")
                    && !strCpf.replace(".","").replace("-","").equals("44444444444")
                    && !strCpf.replace(".","").replace("-","").equals("55555555555")
                    && !strCpf.replace(".","").replace("-","").equals("66666666666")
                    && !strCpf.replace(".","").replace("-","").equals("77777777777")
                    && !strCpf.replace(".","").replace("-","").equals("88888888888")
                    && !strCpf.replace(".","").replace("-","").equals("99999999999")
                    || (strCpf.replace(".","").replace("-","").length() != 11)) {
                try {
                    boolean validado = true;
                    int d1, d2;
                    int digito1, digito2, resto;
                    int digitoCPF;
                    String nDigResult;
                    strCpf = strCpf.replace('.', ' ');
                    strCpf = strCpf.replace('-', ' ');
                    strCpf = strCpf.replaceAll(" ", "");
                    d1 = d2 = 0;
                    digito1 = digito2 = resto = 0;

                    for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
                        digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

                        //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
                        d1 = d1 + (11 - nCount) * digitoCPF;

                        //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.  
                        d2 = d2 + (12 - nCount) * digitoCPF;
                    };

                    //Primeiro resto da divisão por 11.  
                    resto = (d1 % 11);

                    //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
                    if (resto < 2) {
                        digito1 = 0;
                    } else {
                        digito1 = 11 - resto;
                    }

                    d2 += 2 * digito1;

                    //Segundo resto da divisão por 11.  
                    resto = (d2 % 11);

                    //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
                    if (resto < 2) {
                        digito2 = 0;
                    } else {
                        digito2 = 11 - resto;
                    }

                    //Digito verificador do CPF que está sendo validado.  
                    String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

                    //Concatenando o primeiro resto com o segundo.  
                    nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

                    //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.  
                    return nDigVerific.equals(nDigResult);
                } catch (Exception e) {
                    System.err.println("Erro !" + e);
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }
}