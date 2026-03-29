# 🗺️ EasyPet Backend - Roadmap de Tarefas Futuras

Este documento mapeia ideias e features estratégicas para a evolução futura da aplicação de Mínimo Produto Viável (MVP) para um ecossistema Enterprise (SaaS).

## Infraestrutura Atual ⚙️
- **Deploy:** Railway ✅
- **Containers:** Dockerizado ✅

---

## 🚀 Backlog do Produto (Próximos Passos)

### 1. Upload de Arquivos (Fotos e Exames) 📸
* **A Ideia:** Permitir o upload da foto de perfil do Pet e anexar arquivos de resultados de exames (PDF, JPG) diretamente no Prontuário Médico (`MedicalRecord`).
* **Como Implementar:** Integração com **Amazon S3** ou **Cloudinary**. Criar o endpoint `POST /api/pets/{id}/photo` e `POST /api/vets/appointments/{id}/records/attachments` salvando as URLs no Banco.

### 2. Mensageria Avançada (WhatsApp e SMS) 📱
* **A Ideia:** Aumentar massivamente a taxa de abertura dos lembretes do `VaccineScheduler` e de novos Agendamentos, enviando os alertas direto no celular do tutor.
* **Como Implementar:** Integração com a API do **Twilio**, **Z-API** ou **Evolution API**, chamando-os via WebHook ou disparo assíncrono das tarefas agendadas atuais de forma paralela ao e-mail.

### 3. Histórico Médico Imutável e Auditoria 🔒
* **A Ideia:** Atender as normativas dos conselhos de medicina veterinária e LGPD, registrando quem alterou qualquer linha de um diagnóstico ou receituário, mantendo as versões anteriores imutáveis rastreáveis.
* **Como Implementar:** Configurar a framework **Hibernate Envers** usando anotações como `@Audited` sobre a entidade Medical Record. Isso cria magicamente tabelas de log `_AUD` no Flyway registrando versões da tupla.

### 4. Pagamentos e Faturamento Integrados (Billing) 💳
* **A Ideia:** Transformar a jornada final tornando a API do EasyPet responsável por gerenciar também as finanças entre Tutores e a Clínica.
* **Como Implementar:** Integração com **Stripe** ou **Mercado Pago** gerando um link de cobrança/QR Code do Pix. Criar a entidade e Controller de `Invoice` e atrelá-la internamente à entidade `Appointment`.

### 5. Dashboards e Métricas Analíticas 📊
* **A Ideia:** Entregar valor visual e de negócios para o Administrador da Clínica (Quantas consultas marcadas hoje? Faturamento? Top vacinas aplicadas?).
* **Como Implementar:** Criar um módulo de relatórios através de um `DashboardController` fazendo queries SQL avançadas agregando as informações gerais das entidades.
