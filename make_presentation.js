const pptxgen = require('pptxgenjs');
const pptx = new pptxgen();
pptx.layout = 'LAYOUT_WIDE';
pptx.author = 'Online Recipe Sharing Platform Team';
pptx.subject = 'Java Programming Review 1';
pptx.title = 'Online Recipe Sharing Platform - Review 1';
pptx.company = 'Galgotias University';
pptx.lang = 'en-IN';
pptx.theme = {
  headFontFace: 'Aptos Display',
  bodyFontFace: 'Aptos',
  lang: 'en-US'
};
pptx.defineSlideMaster({
  title: 'MASTER',
  background: { color: 'F5F1EB' },
  objects: [
    { rect: { x:0, y:7.18, w:13.333, h:0.32, fill: { color:'26231F' }, line: { color:'26231F' } } },
    { text: { text:'ONLINE RECIPE SHARING PLATFORM  |  REVIEW 1', options:{ x:0.45, y:7.21, w:6.5, h:0.18, fontFace:'Aptos', fontSize:8, color:'FFFDF9', margin:0, bold:true, charSpacing:0.4 } } },
    { text: { text:'Java Programming • 2026', options:{ x:10.7, y:7.21, w:2.15, h:0.18, fontFace:'Aptos', fontSize:8, color:'E9E4DE', margin:0, align:'right' } } }
  ],
  slideNumber: { x:12.92, y:7.21, color:'FFFDF9', fontFace:'Aptos', fontSize:8 }
});

const C = { ink:'26231F', muted:'77716A', line:'E5DED5', paper:'FFFDF9', soft:'F7F3EE', accent:'E45B37', accentDark:'A74226', green:'467B57', gold:'C79535', blue:'426B95', white:'FFFFFF' };
function addTitle(slide, kicker, title, sub='') {
  slide.addText(kicker.toUpperCase(), { x:0.55,y:0.42,w:4.8,h:0.25,fontSize:10,bold:true,color:C.accentDark,charSpacing:1.3,margin:0 });
  slide.addText(title, { x:0.55,y:0.72,w:12.2,h:0.62,fontSize:27,bold:true,color:C.ink,margin:0,breakLine:false,fit:'shrink' });
  if (sub) slide.addText(sub, { x:0.55,y:1.34,w:11.8,h:0.42,fontSize:12.5,color:C.muted,margin:0,fit:'shrink' });
}
function box(slide,x,y,w,h,fill=C.paper,line=C.line,r=0.16){
  slide.addShape(pptx.ShapeType.roundRect,{x,y,w,h,rectRadius:r,fill:{color:fill},line:{color:line,width:1.1}});
}
function txt(slide,text,x,y,w,h,fs=12,color=C.ink,bold=false,align='left'){
  slide.addText(text,{x,y,w,h,fontSize:fs,color,bold,margin:0,fit:'shrink',valign:'mid',align,breakLine:false});
}
function pill(slide,text,x,y,w,fill=C.ink,color=C.white){
  slide.addShape(pptx.ShapeType.roundRect,{x,y,w,h:0.3,rectRadius:0.13,fill:{color:fill},line:{color:fill}});
  txt(slide,text,x,y+0.015,w,0.27,8.5,color,true,'center');
}
function bullet(slide,text,x,y,w,h,fs=12){
  slide.addShape(pptx.ShapeType.ellipse,{x,y:y+0.06,w:0.07,h:0.07,fill:{color:C.accent},line:{color:C.accent}});
  txt(slide,text,x+0.16,y,w-0.16,h,fs,C.ink,false,'left');
}
function arrow(slide,x1,y1,x2,y2,color=C.muted,width=1.3){
  slide.addShape(pptx.ShapeType.line,{x:x1,y:y1,w:x2-x1,h:y2-y1,line:{color,width,beginArrowType:'none',endArrowType:'triangle'}});
}
function addNote(slide, text){
  slide.addNotes(text);
}

// 1 Title
{
  const s=pptx.addSlide('MASTER');
  s.background={color:C.ink};
  s.addShape(pptx.ShapeType.arc,{x:8.7,y:-0.7,w:5.6,h:5.6,line:{color:C.accent,width:3,transparency:18},adjustPoint:0.35});
  s.addShape(pptx.ShapeType.ellipse,{x:9.75,y:1.05,w:2.8,h:2.8,fill:{color:C.accent,transparency:6},line:{color:C.accent}});
  s.addText('🍴', {x:10.52,y:1.7,w:1.2,h:0.7,fontSize:30,align:'center',margin:0,color:C.white});
  s.addText('ONLINE RECIPE\nSHARING PLATFORM',{x:0.7,y:1.55,w:7.6,h:1.35,fontSize:30,bold:true,color:C.white,margin:0,breakLine:false,fit:'shrink',valign:'mid'});
  s.addText('Java Web Project • Review 1',{x:0.72,y:3.1,w:4.5,h:0.35,fontSize:15,bold:true,color:'F0C9BB',margin:0});
  s.addText('A role-based community for publishing, discovering, rating and saving recipes.',{x:0.72,y:3.55,w:6.8,h:0.72,fontSize:18,color:'F2EAE3',margin:0,fit:'shrink'});
  pill(s,'WEB-BASED',0.72,4.65,1.15,C.accent,C.white);
  pill(s,'JSP + SERVLETS',2.0,4.65,1.55,'3C332E',C.white);
  pill(s,'JDBC + MYSQL',3.7,4.65,1.45,'3C332E',C.white);
  s.addText('Prepared for Galgotias University • 2026',{x:0.72,y:6.36,w:5.3,h:0.26,fontSize:10,color:'BEB3AB',margin:0});
  addNote(s,'Opening slide. The uploaded university guidelines state Review 1 carries 33 marks and the project is evaluated using either the GUI-based or Web-based rubric.');
}

// 2 Why web
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'01 • Project direction','Why the web-based option is the lower-risk choice','Decision based on the project idea, the two-day build window, and the supplied Web-based marking rubric.');
  box(s,0.55,2.0,5.95,4.65,C.paper,C.line);
  txt(s,'Decision',0.85,2.28,1.6,0.25,10,C.accentDark,true);
  txt(s,'Build it as a Java Web application.',0.85,2.62,5.0,0.7,22,C.ink,true);
  bullet(s,'Fits the Instagram / Reddit-style community UI naturally.',0.85,3.52,5.0,0.45,12);
  bullet(s,'JSP + Servlets make request/response and sessions easy to demonstrate.',0.85,4.03,5.0,0.65,12);
  bullet(s,'JDBC maps cleanly to the required database integration.',0.85,4.78,5.0,0.45,12);
  bullet(s,'Responsive HTML/CSS can be shown directly in browser screenshots.',0.85,5.3,5.0,0.6,12);
  pill(s,'LOWER BUILD RISK',0.85,6.08,1.5,C.green,C.white);

  box(s,6.75,2.0,6.0,4.65,C.soft,C.line);
  txt(s,'Review 1 rubric alignment',7.05,2.28,3.5,0.25,10,C.accentDark,true);
  const rows=[['Problem Understanding & Solution Design','8'],['Core Java Concepts','10'],['Database Integration (JDBC)','8'],['Servlets & Web Integration','7']];
  rows.forEach((r,i)=>{
    const y=2.83+i*0.8;
    s.addShape(pptx.ShapeType.line,{x:7.05,y:y+0.62,w:5.35,h:0,line:{color:C.line,width:1}});
    txt(s,r[0],7.05,y,4.35,0.38,12,C.ink,i===1);
    txt(s,r[1],11.85,y,0.55,0.38,17,C.accentDark,true,'right');
    txt(s,'marks',12.38,y+0.05,0.38,0.26,8,C.muted,false,'right');
  });
  txt(s,'Review 1 total',7.05,6.11,2.0,0.35,13,C.ink,true);
  txt(s,'33',11.82,6.05,0.62,0.45,23,C.ink,true,'right');
  addNote(s,'The university PDF explicitly separates GUI-based and Web-based rubrics. The Web-based table on page 3 gives 8 + 10 + 8 + 7 = 33 marks for Review 1.');
}

// 3 Product map
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'02 • Solution design','One platform, three role experiences','Shared feed and recipe pages, with role-specific dashboards and permissions.');
  // center platform
  box(s,5.0,2.1,3.3,3.6,C.paper,C.line);
  txt(s,'RecipeHub',5.35,2.45,2.6,0.45,23,C.ink,true,'center');
  pill(s,'SHARED PLATFORM',5.82,3.0,1.65,C.accent,C.white);
  txt(s,'Explore recipes\nRecipe detail\nRatings & reviews\nCollections\nMessages',5.45,3.6,2.4,1.35,13,C.muted,false,'center');
  // roles
  box(s,0.65,2.2,3.55,1.45,'FFF3EE','EED8CE');
  pill(s,'ADMIN',0.9,2.48,0.78,C.accent,C.white);
  txt(s,'Users • approvals • moderation • settings',1.0,2.88,2.8,0.42,12,C.ink,true);
  arrow(s,4.2,2.9,5.0,2.9,C.accent,1.5);
  box(s,0.65,4.35,3.55,1.45,'FFF9E9','E9DDBB');
  pill(s,'CONTRIBUTOR',0.9,4.63,1.12,C.gold,C.white);
  txt(s,'Share recipes • edit • messages • stats',1.0,5.03,2.8,0.42,12,C.ink,true);
  arrow(s,4.2,5.02,5.0,5.02,C.gold,1.5);
  box(s,9.05,2.2,3.55,1.45,'EDF7F0','CFE0D2');
  pill(s,'EXPLORER',9.3,2.48,0.9,C.green,C.white);
  txt(s,'Search • rate • review • collect • history',9.4,2.88,2.8,0.42,12,C.ink,true);
  arrow(s,9.05,2.9,8.3,2.9,C.green,1.5);
  box(s,9.05,4.35,3.55,1.45,C.soft,C.line);
  txt(s,'Shared design language',9.35,4.65,2.9,0.28,11,C.accentDark,true);
  txt(s,'Cards • filters • tables • dashboards • responsive layout',9.35,5.05,2.9,0.52,11,C.muted,false);
  arrow(s,9.05,5.02,8.3,5.02,C.muted,1.2);
  addNote(s,'This is the role map used in the design. Each user type has a distinct dashboard while the feed and recipe detail remain common.');
}

// 4 Architecture
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'03 • Technical architecture','Simple MVC-style Java web architecture','Keep the layers obvious so the reviewer can trace one request from browser to database.');
  const layers=[
    ['Browser / UI','HTML5 • CSS3 • JSP','F5F1EB'],
    ['Controller','Jakarta Servlets • request/response • session','FFF3EE'],
    ['Service','Authentication • recipe rules • validation','FFF9E9'],
    ['DAO','UserDAO • RecipeDAO • CRUDDAO<T>','EDF7F0'],
    ['JDBC / MySQL','PreparedStatement • ResultSet • normalized tables','EEF3F7']
  ];
  layers.forEach((l,i)=>{
    const y=1.95+i*0.86;
    box(s,1.2,y,10.8,0.62,l[2],C.line);
    txt(s,l[0],1.5,y+0.12,2.2,0.3,14,C.ink,true);
    txt(s,l[1],3.65,y+0.12,7.9,0.3,12,C.muted,false);
    if(i<layers.length-1) arrow(s,6.58,y+0.62,6.58,y+0.84,C.muted,1.0);
  });
  box(s,0.75,6.39,11.8,0.52,C.ink,C.ink);
  txt(s,'Cross-cutting: AuthFilter • application exceptions • session user • role checks',1.05,6.52,11.2,0.24,10.5,C.white,true,'center');
  addNote(s,'The project uses a small MVC-style separation: Servlets handle HTTP, services hold business rules, DAOs perform JDBC, and models hold data.');
}

// 5 Database design
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'04 • Database design','Normalized schema for the platform','The design separates users, recipes, recipe parts, reviews, collections, messages, history and system settings.');
  const tables=[
    ['users','id PK\nname\nemail\nrole\nactive'],
    ['recipes','id PK\nauthor_id FK\ntitle\nstatus\nviews'],
    ['ingredients','id PK\nrecipe_id FK\nitem_name\nquantity'],
    ['instructions','id PK\nrecipe_id FK\nstep_no\ntext'],
    ['reviews','id PK\nrecipe_id FK\nuser_id FK\nrating'],
    ['collections','id PK\nuser_id FK\nname'],
    ['collection_items','collection_id FK\nrecipe_id FK'],
    ['messages','id PK\nsender_id FK\nreceiver_id FK\nmessage'],
    ['browsing_history','id PK\nuser_id FK\nrecipe_id FK\nviewed_at'],
    ['system_settings','setting_key PK\nsetting_value']
  ];
  const pos=[
    [0.55,2.0],[3.2,2.0],[6.0,2.0],[8.8,2.0],[3.2,4.2],[6.0,4.2],[8.8,4.2],[0.55,4.35],[0.55,5.62],[8.8,5.82]
  ];
  tables.forEach((t,i)=>{
    const [x,y]=pos[i];
    const h=(i===0||i===1)?1.35:1.15;
    box(s,x,y,2.25,h,C.paper,C.line);
    txt(s,t[0],x+0.12,y+0.1,2.0,0.26,11.5,C.accentDark,true);
    txt(s,t[1],x+0.12,y+0.43,2.0,h-0.48,9.5,C.muted,false);
  });
  // relationship lines, intentionally readable rather than every FK
  arrow(s,2.8,2.55,3.2,2.55,C.muted,1.0);
  arrow(s,4.35,3.35,4.35,4.2,C.muted,1.0);
  arrow(s,7.15,3.35,7.15,4.2,C.muted,1.0);
  arrow(s,9.95,3.35,9.95,4.2,C.muted,1.0);
  arrow(s,2.8,5.0,3.2,2.95,C.muted,1.0);
  arrow(s,2.8,5.98,8.8,4.82,C.muted,1.0);
  txt(s,'Core relationships',9.05,5.17,2.0,0.2,9,C.muted,true);
  txt(s,'users → recipes\nrecipes → ingredients / instructions / reviews\nusers ↔ collections / messages / history',9.05,5.42,3.4,0.92,9.5,C.ink,false);
  addNote(s,'Schema source: database/schema.sql in the supplied Review 1 project pack. Main foreign-key paths are users→recipes, recipes→ingredients/instructions/reviews, and user-owned collections, messages and browsing history.');
}

// 6 UI wireframes
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'05 • UI / UX','A social-feed style interface without copying a specific platform','Warm editorial cards, simple navigation, clear hierarchy and mobile-safe layouts.');
  // browser frame left
  box(s,0.55,1.95,6.0,4.65,C.paper,C.line);
  txt(s,'SIGN IN',0.85,2.22,1.2,0.25,10,C.accentDark,true);
  txt(s,'Good recipes deserve\ngood company.',0.85,2.63,3.9,0.75,23,C.ink,true);
  s.addShape(pptx.ShapeType.roundRect,{x:4.7,y:2.35,w:1.45,h:1.45,rectRadius:0.22,fill:{color:C.accent},line:{color:C.accent}});
  txt(s,'🍴',4.96,2.69,0.9,0.5,24,C.white,true,'center');
  ['Email','Password'].forEach((label,i)=>{
    txt(s,label,0.9,3.63+i*0.62,1.0,0.18,9,C.muted,true);
    s.addShape(pptx.ShapeType.roundRect,{x:0.9,y:3.88+i*0.62,w:4.45,h:0.4,rectRadius:0.1,fill:{color:'F8F5F0'},line:{color:C.line,width:0.8}});
  });
  s.addShape(pptx.ShapeType.roundRect,{x:0.9,y:5.13,w:4.45,h:0.43,rectRadius:0.1,fill:{color:C.ink},line:{color:C.ink}});
  txt(s,'Sign in',0.9,5.19,4.45,0.28,10,C.white,true,'center');
  // feed frame
  box(s,6.85,1.95,5.93,4.65,C.paper,C.line);
  txt(s,'EXPLORE RECIPES',7.15,2.22,2.3,0.25,10,C.accentDark,true);
  txt(s,"What's cooking today?",7.15,2.57,4.5,0.43,21,C.ink,true);
  s.addShape(pptx.ShapeType.roundRect,{x:7.15,y:3.15,w:4.65,h:0.43,rectRadius:0.1,fill:{color:'F7F4EF'},line:{color:C.line}});
  txt(s,'Search recipes, cuisines, ingredients...',7.32,3.26,4.3,0.2,9.5,C.muted,false);
  // two cards
  [7.15,9.55].forEach((x,i)=>{
    s.addShape(pptx.ShapeType.roundRect,{x,y:3.88,w:2.2,h:2.0,rectRadius:0.15,fill:{color:i===0?'E9D4BD':'D9C9B2'},line:{color:C.line}});
    s.addShape(pptx.ShapeType.ellipse,{x:x+0.55,y:4.14,w:1.1,h:0.85,fill:{color:i===0?'C46E39':'9C6B3C'},line:{color:i===0?'C46E39':'9C6B3C'}});
    txt(s,i===0?'Creamy Garlic\nPasta':'Masala Vegetable\nToast',x+0.16,5.1,1.9,0.5,10,C.ink,true,'center');
    txt(s,i===0?'★ 4.8 • 128 views':'★ 4.5 • 94 views',x+0.16,5.72,1.9,0.22,8.5,C.muted,false,'center');
  });
  txt(s,'Design principles: consistent cards • strong whitespace • responsive grid • visible role context',6.95,6.22,5.55,0.23,9.5,C.muted,false,'center');
  addNote(s,'The UI direction is intentionally original: social-feed behaviour is familiar, but the visual language is a warm editorial recipe app rather than a direct clone of Instagram, Facebook or Reddit.');
}

// 7 Role dashboards
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'06 • Role dashboards','The requirements map directly into four dashboard areas','Each dashboard emphasizes the actions that belong to that role instead of putting every feature into one screen.');
  const cards=[
    {x:0.55,y:1.95,w:6.0,h:2.0,tag:'ADMIN',tagFill:C.accent,title:'Control center',items:['User management table','Recipe approval queue','Content moderation','System settings']},
    {x:6.78,y:1.95,w:6.0,h:2.0,tag:'CONTRIBUTOR',tagFill:C.gold,title:'My kitchen',items:['Personal recipe management','Interaction history','Views / ratings / feedback stats','Profile management']},
    {x:0.55,y:4.25,w:6.0,h:2.0,tag:'EXPLORER',tagFill:C.green,title:'My collection',items:['Browsing history','Ratings and reviews','Recipe collections','Profile & preferences']},
    {x:6.78,y:4.25,w:6.0,h:2.0,tag:'SHARED',tagFill:C.ink,title:'Recipe experience',items:['Search + filters','Recipe detail','Save / collection action','Community feedback']}
  ];
  cards.forEach(c=>{
    box(s,c.x,c.y,c.w,c.h,C.paper,C.line);
    pill(s,c.tag,c.x+0.25,c.y+0.25,Math.max(0.82,c.tag.length*0.09),c.tagFill,C.white);
    txt(s,c.title,c.x+0.25,c.y+0.69,c.w-0.5,0.34,17,C.ink,true);
    c.items.forEach((it,j)=>bullet(s,it,c.x+0.25,c.y+1.12+j*0.23,c.w-0.5,0.22,10));
  });
  addNote(s,'This slide is the placement plan for the organizer requirements. The dashboards are distinct, while the common recipe feed and detail screen are shared.');
}

// 8 Review 1 implementation coverage
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'07 • Review 1 readiness','What is already structured in the repository','The goal is to submit a coherent architecture, schema, connectivity layer and UI plan now, then expand the same structure for Review 2.');
  const rows=[
    ['Problem / solution design','Role map + feature map + navigation plan','READY'],
    ['Core Java','Models, enums, generic DAO contract, custom exceptions, service layer, async StatsService','READY'],
    ['Database / JDBC','MySQL schema, foreign keys, indexes, PreparedStatement DAOs, connection utility','READY'],
    ['Servlets / Web','Login, logout, feed, recipe detail, dashboards, AuthFilter, JSP views','READY'],
  ];
  rows.forEach((r,i)=>{
    const y=1.95+i*1.07;
    box(s,0.7,y,11.9,0.86,i%2===0?C.paper:C.soft,C.line);
    txt(s,r[0],0.95,y+0.16,2.35,0.23,11.5,C.ink,true);
    txt(s,r[1],3.38,y+0.13,7.45,0.48,10.5,C.muted,false);
    pill(s,r[2],11.35,y+0.26,0.95,C.green,C.white);
  });
  box(s,0.7,6.33,11.9,0.45,C.ink,C.ink);
  txt(s,'Review 1 deliverables: presentation + GitHub repository + README + database files + project structure',0.95,6.43,11.3,0.22,9.5,C.white,true,'center');
  addNote(s,'Review 1 requires a presentation and GitHub link. The university document states Review 1 is 33 marks and the Web-based rubric covers solution design, core Java, JDBC and Servlets/Web Integration.');
}

// 9 Plan
{
  const s=pptx.addSlide('MASTER');
  addTitle(s,'08 • Execution plan','Two focused build days, then incremental completion','Do not redesign the project after Review 1. Keep this structure and add the remaining CRUD operations and final business features.');
  const cols=[
    {x:0.55,title:'DAY 1',sub:'Build foundation',items:['Create Maven project','Run schema + seed SQL','Verify JDBC connection','Implement login / sessions','Wire feed + recipe detail','Commit to GitHub']},
    {x:4.56,title:'DAY 2',sub:'Polish + submit',items:['Finish dashboard screens','Add screenshots / diagrams','Clean README + .gitignore','Run a smoke test','Prepare presentation PDF/PPT','Submit before deadline']},
    {x:8.57,title:'REVIEW 2',sub:'Finish functionality',items:['Recipe CRUD','Approve / reject','Ratings + reviews','Collections + history','Messaging + stats','Testing + code cleanup']}
  ];
  cols.forEach((c,idx)=>{
    box(s,c.x,2.0,3.62,4.55,idx===0?'FFF3EE':idx===1?'FFF9E9':C.paper,C.line);
    pill(s,c.title,c.x+0.25,2.3,0.9,idx===0?C.accent:idx===1?C.gold:C.green,C.white);
    txt(s,c.sub,c.x+0.25,2.75,3.0,0.35,18,C.ink,true);
    c.items.forEach((it,j)=>bullet(s,it,c.x+0.25,3.36+j*0.5,3.05,0.38,10.5));
  });
  txt(s,'Deadline to protect:',0.57,6.78,1.55,0.22,9.5,C.muted,true);
  txt(s,'Saturday, 10 October 2026',2.05,6.73,3.2,0.3,12,C.accentDark,true);
  txt(s,'Review 2 deadline:',8.58,6.78,1.52,0.22,9.5,C.muted,true);
  txt(s,'Sunday, 15 November 2026',10.1,6.73,2.25,0.3,11,C.ink,true);
  addNote(s,'The university document gives 10 October 2026 as the Review 1 deadline and 15 November 2026 as the Review 2 deadline.');
}

pptx.writeFile({ fileName:'/mnt/data/OnlineRecipeSharingPlatform_Review1/Online_Recipe_Sharing_Platform_Review1.pptx' });
