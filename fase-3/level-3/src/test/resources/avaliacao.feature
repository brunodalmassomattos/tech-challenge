Feature: Gerenciamento de Avaliacao
	
	Scenario: Listar avaliacao por ID
		    Given um avaliacao com ID "c28e78ec-b895-4230-9a38-a81097abc112"
		    When o sistema retorna a avalicao por esse ID
		    Then o sistema retorna detalhes da avaliacao
		    And a nota da avaliacao é 3
		    And o comentario da avaliacao é "Horrivel"
	    
	Scenario: Alterar nota avaliacao por Avalicao ID
		    Given um avaliacao com ID "c28e78ec-b895-4230-9a38-a81097abc112"
				When a nota da avalicao é alterada para 3
				Then a nota da avaliacao é atualizada com sucesso
				And a nova nota da avaliacao é 3
		
	Scenario: Alterar comentario avaliacao por Avalicao ID
		    Given um avaliacao com ID "c28e78ec-b895-4230-9a38-a81097abc112"
				When o comentario da avalicao é alterado para "Horrivel"
				Then o comentario da avaliacao é atualizado com sucesso
				And o novo comentario da avaliacao é "Horrivel"
		
    
