package com.stocktrackerapi.util;

import java.util.List;
import java.util.Optional;

import com.stocktrackerapi.document.Alias;
import com.stocktrackerapi.document.Symbol;
import com.stocktrackerapi.repository.AliasRepository;
import com.stocktrackerapi.repository.SymbolRepository;

public class SymbolUtils {
	
	
	
	private AliasRepository aliasRepository;
	private SymbolRepository symbolRepository;
	
	
	public SymbolUtils (AliasRepository aliasRepository, SymbolRepository symbolRepository) {
		this.aliasRepository = aliasRepository;
		this.symbolRepository = symbolRepository;
		}
	
	
	public String getSymbolFromAlias(String alias) {
		
		String symbolFromAlias = alias;
		Optional<List<Alias>> optAlias = aliasRepository.findByAlias(alias);
		
		if(optAlias.isPresent()) {
			symbolFromAlias = optAlias.get().get(0).getSymbol();
		}
		return symbolFromAlias;		
	}
	
	public String getAliasFromSymbol(String symbol) {
		
		String aliasFromSymbol = symbol;
		Optional<List<Alias>> optAlias = aliasRepository.findBySymbol(symbol);
		
		if(optAlias.isPresent()) {
			aliasFromSymbol = optAlias.get().get(0).getAlias();
		}
		return aliasFromSymbol;		
	}
	
	
	/*
	 * Returns the number of updated symbols
	 */	
    public int updateSymbolsFromAliases(){
    	
    	int updateCount = 0;

        List<Alias> aliases = aliasRepository.findAll();
        Optional<Symbol> optSymbol;
        Symbol symbol;

        for(Alias alias : aliases){

            //update symbols
            optSymbol = symbolRepository.findBySymbol(alias.getSymbol());

            if(optSymbol.isPresent()){
                symbol = optSymbol.get();
                symbol.setAlias(alias.getAlias());
                symbolRepository.save(symbol);
                updateCount++;
            } else {
                System.out.println("Symbol not found for alias " + alias.getAlias());
            }
        }
        
        return updateCount;
    }	

}
