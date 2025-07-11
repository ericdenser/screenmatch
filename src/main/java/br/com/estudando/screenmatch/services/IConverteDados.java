package br.com.estudando.screenmatch.services;

public interface IConverteDados {
  <T> T obterDados(String json, Class<T> classe);
}
