package br.com.estudando.screenmatch.principal;

import br.com.estudando.screenmatch.model.DadosEpisodio;
import br.com.estudando.screenmatch.model.DadosSerie;
import br.com.estudando.screenmatch.model.DadosTemporada;
import br.com.estudando.screenmatch.model.Episodio;
import br.com.estudando.screenmatch.services.ConsumoAPI;
import br.com.estudando.screenmatch.services.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner read = new Scanner(System.in);

    private ConsumoAPI consumo = new ConsumoAPI();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";

    private final String APIKEY = "&apikey=247578c7";

    public void exibeMenu() {
        System.out.println("Informe o nome da série que deseja buscar: ");
        var nomeSerie = read.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + APIKEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 Episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite um trecho do titulo do episodio para busca-lo: ");
        var trechoTitulo = read.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada()
            + "\nEpisódio: " + episodioBuscado.get().getTitulo() );
        } else {
            System.out.println("Episodio nao encontrado");
        }
    }
}
