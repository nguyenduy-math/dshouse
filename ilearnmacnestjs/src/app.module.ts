import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { N8nController } from './n8n/n8n.controller';

@Module({
  imports: [],
  controllers: [AppController, N8nController],
  providers: [AppService],
})
export class AppModule {}
