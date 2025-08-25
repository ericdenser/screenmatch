package br.com.estudando.screenmatch.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;


public class ConsultaGemini {

    public static String obterResposta(String texto) {
        try {
            Client client = new Client();

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            "Retorne apenas uma traducao para portugues do texto: " + texto,
                            null);
            return response.text();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return "GEMINI API falhou";
    }

}
