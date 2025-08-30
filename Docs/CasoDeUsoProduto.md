usecaseDiagram
actor Funcionario as F

rectangle Sistema {
  usecase "Cadastrar Produto" as UC1
  usecase "Alterar Produto" as UC2
  usecase "Deletar Produto" as UC3
  usecase "Consultar Produto" as UC4
}

F --> UC1
F --> UC2
F --> UC3
F --> UC4

note right of UC1
  Dados necessários:
  - Descrição
  - Unidade de Medida
  - Valor
  - Quantidade
  - Categoria
  - Ativar/Inativar
end note

note bottom of F
  O Funcionário precisa:
  - Estar logado
  - Ter permissão
end note
