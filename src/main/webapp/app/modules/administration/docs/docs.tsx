import './docs.scss';

import React from 'react';
import { Card } from 'reactstrap';

const DocsPage = () => (
  <Card className="jh-card">
  <div>
    <iframe src="../swagger-ui/index.html" width="100%" height="800" title="Swagger UI" seamless style={{ border: 'none' }} />
  </div>
  </Card>
);

export default DocsPage;
