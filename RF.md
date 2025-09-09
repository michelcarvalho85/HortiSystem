```mermaid

usecaseDiagram

actor Administrador as ADM

actor Gerente as GER

actor Caixa as CAI

actor Estoquista as EST

actor Financeiro as FIN

actor Cliente as CLI

actor Sistema as SYS

actor "Todos os usuários" as ALL



rectangle Sistema {

&nbsp; usecase "RF001 - Manter Usuário" as RF001

&nbsp; usecase "RF002 - Autenticar login" as RF002

&nbsp; usecase "RF003 - Alterar Senha" as RF003

&nbsp; usecase "RF004 - Manter Categoria" as RF004

&nbsp; usecase "RF005 - Manter Produto" as RF005

&nbsp; usecase "RF006 - Consultar Produtos" as RF006

&nbsp; usecase "RF007 - Manter Fornecedor" as RF007

&nbsp; usecase "RF008 - Manter Compra" as RF008

&nbsp; usecase "RF009 - Registrar Itens da Compra" as RF009

&nbsp; usecase "RF010 - Atualizar Estoque na Compra" as RF010

&nbsp; usecase "RF011 - Controlar Estoque" as RF011

&nbsp; usecase "RF012 - Notificar Validade" as RF012

&nbsp; usecase "RF013 - Manter Cliente" as RF013

&nbsp; usecase "RF014 - Registrar Venda" as RF014

&nbsp; usecase "RF015 - Atualizar Estoque na Venda" as RF015

&nbsp; usecase "RF016 - Controlar Status de Venda" as RF016

&nbsp; usecase "RF017 - Gerar Contas a Receber" as RF017

&nbsp; usecase "RF018 - Gerenciar Contas a Receber" as RF018

&nbsp; usecase "RF019 - Registrar Desperdício" as RF019

&nbsp; usecase "RF020 - Consultar Desperdício" as RF020

&nbsp; usecase "RF021 - Gerar Relatórios" as RF021

&nbsp; usecase "RF022 - Registrar Ações no Log" as RF022

&nbsp; usecase "RF023 - Registrar Lotes" as RF023

&nbsp; usecase "RF024 - Rastrear Lote" as RF024

&nbsp; usecase "RF025 - Realizar Backup do Sistema" as RF025

&nbsp; usecase "RF026 - Controlar Perfis" as RF026

&nbsp; usecase "RF027 - Atribuir Permissões" as RF027

&nbsp; usecase "RF028 - Registrar Consentimento LGPD" as RF028

&nbsp; usecase "RF029 - Revogar Consentimento" as RF029

&nbsp; usecase "RF030 - Registrar Solicitações LGPD" as RF030

&nbsp; usecase "RF031 - Registrar Resposta à Solicitação LGPD" as RF031

}



ADM --> RF001

ADM --> RF004

ADM --> RF021

ADM --> RF025

ADM --> RF026

ADM --> RF027

ADM --> RF031



ALL --> RF002

ALL --> RF003

ALL --> RF006



GER --> RF004

GER --> RF005

GER --> RF007

GER --> RF008

GER --> RF011

GER --> RF013

GER --> RF016

GER --> RF018

GER --> RF020

GER --> RF021

GER --> RF024

GER --> RF028

GER --> RF029

GER --> RF030

GER --> RF031



CAI --> RF013

CAI --> RF014

CAI --> RF016

CAI --> RF028

CAI --> RF029



EST --> RF005

EST --> RF008

EST --> RF009

EST --> RF011

EST --> RF012

EST --> RF019

EST --> RF023

EST --> RF024



FIN --> RF018



CLI --> RF030



SYS --> RF010

SYS --> RF015

SYS --> RF017

SYS --> RF022



